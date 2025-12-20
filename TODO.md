# TODO: Remove GESTIONNAIRE and MIGRATIONS from Brasil Burger App

## Plan Overview
Remove all GESTIONNAIRE (manager) functionality and MIGRATIONS, keeping only the client-side features for the Brasil Burger application.

## Tasks to Complete


### 1. Remove Gestionnaire Model and References
- [x] Remove Gestionnaire DbSet from ApplicationDbContext
- [x] Remove Gestionnaire-related code from SeedData method
- [x] Update model relationships

### 2. Update AuthController
- [x] Remove Gestionnaire authentication logic
- [x] Simplify to only handle Client authentication
- [x] Update login redirect logic

### 3. Remove Migration Files
- [x] Delete all migration files from Migrations folder (No migration files were found)

### 4. Update HomeController
- [x] Remove Gestionnaire redirect logic

### 5. Clean Up Views
- [x] Remove any Gestionnaire-related views (No Gestionnaire views were found)
- [x] Update navigation/layout to remove manager links
- [x] Fix logout link in _Layout.cshtml

### 6. Update Program.cs
- [x] Ensure no references to Gestionnaire functionality
- [x] Update default route if needed



### 7. Test the Application
- [x] Run the application to verify it works without Gestionnaire
- [x] Test client authentication and ordering process


### 8. Fix Password Hashing Issue
- [x] Fix password hashing inconsistency in SeedData vs AuthController
- [x] Add HashPassword method to ApplicationDbContext
- [x] Update SeedData to use hashed passwords for test clients
- [x] Add DateCreation property for consistency

### 9. Fix Code Structure Issues
- [x] Fix Menu.cs NotMapped attribute usage
- [x] Remove duplicate PanierItem class definition
- [x] Clean up PanierController imports
- [x] Ensure proper compilation without errors
- [x] Remove unused CartController files


### 10. Final Testing
- [x] Application compiles successfully
- [x] All models properly structured
- [x] Controllers use correct model references
- [x] No duplicate class definitions

### 11. Fix JSON Deserialization Issues in CommandeController
- [x] Fix JsonElement null comparison error
- [x] Add proper null checks for commandeData dictionary
- [x] Ensure safe JSON element type checking
- [x] Application compiles without warnings or errors

## Expected Outcome
A clean Brasil Burger application with only client-side functionality, no manager interface, no migration files, proper password hashing consistency, and clean code structure.

