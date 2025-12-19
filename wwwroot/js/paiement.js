let typeCommande = "Livraison";
let paiement = "";

function setType(type) {
    typeCommande = type;
    highlight(event.target);
}

function setPaiement(p) {
    paiement = p;
    highlight(event.target);
}

function highlight(btn) {
    document.querySelectorAll(".choice").forEach(b => b.classList.remove("active"));
    btn.classList.add("active");
}

function payer() {
    if (paiement === "") {
        alert(" Choisissez un mode de paiement");
        return;
    }

    alert(
        " Commande confirmée\n" +
        "Type : " + typeCommande + "\n" +
        "Paiement : " + paiement
    );

    window.location.href = "/Produit";
}
