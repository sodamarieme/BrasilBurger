using BrasilBurgerC.Data;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// MVC
builder.Services.AddControllersWithViews();

// Sessions
builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromHours(1);
    options.Cookie.HttpOnly = true;
    options.Cookie.IsEssential = true;
});

// DbContext SANS migrations
var connectionString = Environment.GetEnvironmentVariable("DATABASE_URL") ?? 
                      builder.Configuration.GetConnectionString("DefaultConnection");
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(connectionString)
);

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
}

app.UseStaticFiles();
app.UseRouting();
app.UseSession();
app.UseAuthorization();

// Middleware pour rediriger vers Login si pas connecté
app.Use(async (context, next) =>
{
    var path = context.Request.Path.Value;
    var userType = context.Session.GetString("UserType");
    
    // Permettre l'accès à Auth sans authentification
    if (!path.StartsWith("/Auth") && !path.StartsWith("/css") && !path.StartsWith("/js") && !path.StartsWith("/images") && !path.StartsWith("/lib"))
    {
        if (string.IsNullOrEmpty(userType))
        {
            context.Response.Redirect("/Auth/Login");
            return;
        }
    }
    
    await next();
});

// ROUTE PAR DÉFAUT
app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Produit}/{action=Index}/{id?}"
);

app.Run();
