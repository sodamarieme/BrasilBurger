let cart = [];

function addToCart(name, price) {
    cart.push({ name, price });
    updateCart();
}

function updateCart() {
    const list = document.getElementById("cartList");
    const totalSpan = document.getElementById("cartTotal");
    const countSpan = document.getElementById("cartCount");

    if (!list) return;

    list.innerHTML = "";
    let total = 0;

    cart.forEach(p => {
        total += p.price;
        list.innerHTML += `<li>${p.name} - ${p.price} FCFA</li>`;
    });

    totalSpan.textContent = total + " FCFA";
    countSpan.textContent = cart.length;
}

function clearCart() {
    cart = [];
    updateCart();
}
