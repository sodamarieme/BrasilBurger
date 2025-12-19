let cart = [];

function addToCart(name, price) {
    cart.push({ name, price });
    document.getElementById("cartCount").textContent = cart.length;
    renderCart();
}

function renderCart() {
    const ul = document.getElementById("cartList");
    const totalSpan = document.getElementById("cartTotal");

    if (!ul || !totalSpan) return;

    ul.innerHTML = "";
    let total = 0;

    cart.forEach(p => {
        total += p.price;
        ul.innerHTML += `
            <li>
                <span>${p.name}</span>
                <span>${p.price} FCFA</span>
            </li>
        `;
    });

    totalSpan.textContent = total + " FCFA";
}

function clearCart() {
    cart = [];
    renderCart();
    document.getElementById("cartCount").textContent = 0;
}

function togglePanier() {
    document.getElementById("panier").style.display = "block";
    document.getElementById("catalogue").style.display = "grid";
}

function showCatalogue() {
    document.getElementById("panier").style.display = "none";
    document.getElementById("catalogue").style.display = "grid";
}

function commander() {
    if (cart.length === 0) {
        alert("Votre panier est vide !");
        return;
    }

    alert("✅ Commande enregistrée !");
    clearCart();
    showCatalogue();
}
