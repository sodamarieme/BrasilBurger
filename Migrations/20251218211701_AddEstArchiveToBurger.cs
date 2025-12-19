using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace BrasilBurgerC.Migrations
{
    /// <inheritdoc />
    public partial class AddEstArchiveToBurger : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "EstArchive",
                table: "Burgers",
                type: "boolean",
                nullable: false,
                defaultValue: false);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "EstArchive",
                table: "Burgers");
        }
    }
}
