using BrasilBurgerC.Data;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// 🔹 MVC
builder.Services.AddControllersWithViews();

// 🔹 PostgreSQL Neon
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection"))
);

// 🔹 SESSIONS (OBLIGATOIRE POUR PANIER & LOGIN)
builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromMinutes(30);
    options.Cookie.HttpOnly = true;
    options.Cookie.IsEssential = true;
});

var app = builder.Build();

// 🔹 Gestion des erreurs
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

// 🔹 FICHIERS STATIQUES (CSS / JS)
app.UseStaticFiles();

// 🔹 ROUTING
app.UseRouting();

// 🔹 SESSIONS
app.UseSession();

// 🔹 AUTH (plus tard)
app.UseAuthorization();

// 🔹 ROUTE MVC PAR DÉFAUT
app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Produit}/{action=Index}/{id?}");


app.Run();
