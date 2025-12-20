using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace BrasilBurgerC.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Burgers",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Nom = table.Column<string>(type: "text", nullable: false),
                    Prix = table.Column<int>(type: "integer", nullable: false),
                    EstArchive = table.Column<bool>(type: "boolean", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Burgers", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Clients",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Nom = table.Column<string>(type: "text", nullable: false),
                    Prenom = table.Column<string>(type: "text", nullable: false),
                    Telephone = table.Column<string>(type: "text", nullable: false),
                    Email = table.Column<string>(type: "text", nullable: false),
                    MotDePasse = table.Column<string>(type: "text", nullable: false),
                    DateCreation = table.Column<DateTime>(type: "timestamp with time zone", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Clients", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Complements",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Nom = table.Column<string>(type: "text", nullable: false),
                    Type = table.Column<string>(type: "text", nullable: false),
                    Prix = table.Column<int>(type: "integer", nullable: false),
                    EstArchive = table.Column<bool>(type: "boolean", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Complements", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Produits",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Nom = table.Column<string>(type: "text", nullable: false),
                    Type = table.Column<string>(type: "text", nullable: false),
                    Prix = table.Column<int>(type: "integer", nullable: false),
                    EstArchive = table.Column<bool>(type: "boolean", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Produits", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Zones",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Nom = table.Column<string>(type: "text", nullable: false),
                    PrixLivraison = table.Column<int>(type: "integer", nullable: false),
                    EstActive = table.Column<bool>(type: "boolean", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Zones", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Menus",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Nom = table.Column<string>(type: "text", nullable: false),
                    Description = table.Column<string>(type: "text", nullable: false),
                    EstArchive = table.Column<bool>(type: "boolean", nullable: false),
                    BurgerId = table.Column<int>(type: "integer", nullable: false),
                    BoissonId = table.Column<int>(type: "integer", nullable: true),
                    FritesId = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Menus", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Menus_Burgers_BurgerId",
                        column: x => x.BurgerId,
                        principalTable: "Burgers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Menus_Complements_BoissonId",
                        column: x => x.BoissonId,
                        principalTable: "Complements",
                        principalColumn: "Id");
                    table.ForeignKey(
                        name: "FK_Menus_Complements_FritesId",
                        column: x => x.FritesId,
                        principalTable: "Complements",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "Livreurs",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Nom = table.Column<string>(type: "text", nullable: false),
                    Prenom = table.Column<string>(type: "text", nullable: false),
                    Telephone = table.Column<string>(type: "text", nullable: false),
                    EstDisponible = table.Column<bool>(type: "boolean", nullable: false),
                    DateCreation = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    ZoneId = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Livreurs", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Livreurs_Zones_ZoneId",
                        column: x => x.ZoneId,
                        principalTable: "Zones",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "Commandes",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    NumeroCommande = table.Column<string>(type: "text", nullable: false),
                    DateCommande = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    TypeCommande = table.Column<int>(type: "integer", nullable: false),
                    Statut = table.Column<int>(type: "integer", nullable: false),
                    ClientId = table.Column<int>(type: "integer", nullable: false),
                    NomComplet = table.Column<string>(type: "text", nullable: false),
                    Telephone = table.Column<string>(type: "text", nullable: false),
                    AdresseLivraison = table.Column<string>(type: "text", nullable: true),
                    ZoneId = table.Column<int>(type: "integer", nullable: true),
                    LivreurId = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Commandes", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Commandes_Clients_ClientId",
                        column: x => x.ClientId,
                        principalTable: "Clients",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Commandes_Livreurs_LivreurId",
                        column: x => x.LivreurId,
                        principalTable: "Livreurs",
                        principalColumn: "Id");
                    table.ForeignKey(
                        name: "FK_Commandes_Zones_ZoneId",
                        column: x => x.ZoneId,
                        principalTable: "Zones",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "CommandeItems",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    CommandeId = table.Column<int>(type: "integer", nullable: false),
                    Quantite = table.Column<int>(type: "integer", nullable: false),
                    PrixUnitaire = table.Column<int>(type: "integer", nullable: false),
                    BurgerId = table.Column<int>(type: "integer", nullable: true),
                    MenuId = table.Column<int>(type: "integer", nullable: true),
                    ComplementId = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CommandeItems", x => x.Id);
                    table.ForeignKey(
                        name: "FK_CommandeItems_Burgers_BurgerId",
                        column: x => x.BurgerId,
                        principalTable: "Burgers",
                        principalColumn: "Id");
                    table.ForeignKey(
                        name: "FK_CommandeItems_Commandes_CommandeId",
                        column: x => x.CommandeId,
                        principalTable: "Commandes",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_CommandeItems_Complements_ComplementId",
                        column: x => x.ComplementId,
                        principalTable: "Complements",
                        principalColumn: "Id");
                    table.ForeignKey(
                        name: "FK_CommandeItems_Menus_MenuId",
                        column: x => x.MenuId,
                        principalTable: "Menus",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "Paiements",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    CommandeId = table.Column<int>(type: "integer", nullable: false),
                    DatePaiement = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    Montant = table.Column<int>(type: "integer", nullable: false),
                    ModePaiement = table.Column<int>(type: "integer", nullable: false),
                    NumeroTransaction = table.Column<string>(type: "text", nullable: true),
                    EstValide = table.Column<bool>(type: "boolean", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Paiements", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Paiements_Commandes_CommandeId",
                        column: x => x.CommandeId,
                        principalTable: "Commandes",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Clients_Email",
                table: "Clients",
                column: "Email",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_Clients_Telephone",
                table: "Clients",
                column: "Telephone",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_CommandeItems_BurgerId",
                table: "CommandeItems",
                column: "BurgerId");

            migrationBuilder.CreateIndex(
                name: "IX_CommandeItems_CommandeId",
                table: "CommandeItems",
                column: "CommandeId");

            migrationBuilder.CreateIndex(
                name: "IX_CommandeItems_ComplementId",
                table: "CommandeItems",
                column: "ComplementId");

            migrationBuilder.CreateIndex(
                name: "IX_CommandeItems_MenuId",
                table: "CommandeItems",
                column: "MenuId");

            migrationBuilder.CreateIndex(
                name: "IX_Commandes_ClientId",
                table: "Commandes",
                column: "ClientId");

            migrationBuilder.CreateIndex(
                name: "IX_Commandes_LivreurId",
                table: "Commandes",
                column: "LivreurId");

            migrationBuilder.CreateIndex(
                name: "IX_Commandes_NumeroCommande",
                table: "Commandes",
                column: "NumeroCommande",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_Commandes_ZoneId",
                table: "Commandes",
                column: "ZoneId");

            migrationBuilder.CreateIndex(
                name: "IX_Livreurs_ZoneId",
                table: "Livreurs",
                column: "ZoneId");

            migrationBuilder.CreateIndex(
                name: "IX_Menus_BoissonId",
                table: "Menus",
                column: "BoissonId");

            migrationBuilder.CreateIndex(
                name: "IX_Menus_BurgerId",
                table: "Menus",
                column: "BurgerId");

            migrationBuilder.CreateIndex(
                name: "IX_Menus_FritesId",
                table: "Menus",
                column: "FritesId");

            migrationBuilder.CreateIndex(
                name: "IX_Paiements_CommandeId",
                table: "Paiements",
                column: "CommandeId",
                unique: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "CommandeItems");

            migrationBuilder.DropTable(
                name: "Paiements");

            migrationBuilder.DropTable(
                name: "Produits");

            migrationBuilder.DropTable(
                name: "Menus");

            migrationBuilder.DropTable(
                name: "Commandes");

            migrationBuilder.DropTable(
                name: "Burgers");

            migrationBuilder.DropTable(
                name: "Complements");

            migrationBuilder.DropTable(
                name: "Clients");

            migrationBuilder.DropTable(
                name: "Livreurs");

            migrationBuilder.DropTable(
                name: "Zones");
        }
    }
}
