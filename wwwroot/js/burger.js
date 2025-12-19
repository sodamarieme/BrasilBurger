let cart = [];

// AJOUT AU PANIER
function addToCart(id, name, price) {
    cart.push({ id, name, price });
    document.getElementById("cartBadge").textContent = cart.length;
}

// NAVIGATION
function showPage(id) {
    document.querySelectorAll(".page").forEach(p => p.classList.remove("active"));
    document.getElementById(id).classList.add("active");
    if (id === "cartPage") showCart();
}

// AFFICHAGE PANIER
function showCart() {
    const cartItems = document.getElementById("cartItems");
    cartItems.innerHTML = "";

    if (cart.length === 0) {
        cartItems.innerHTML = "<p>Votre panier est vide 🛒</p>";
        return;
    }

    cart.forEach(p => {
        cartItems.innerHTML += `<p>${p.name} - ${p.price} FCFA</p>`;
    });
}

// LOGIN
function login() {
    loginPage.classList.remove("active");
    navBar.style.display = "block";
    showPage("productsPage");
}

function logout() {
    navBar.style.display = "none";
    document.querySelectorAll(".page").forEach(p => p.classList.remove("active"));
    loginPage.classList.add("active");
}

