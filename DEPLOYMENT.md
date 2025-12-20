# Déploiement Brasil Burger sur Render

## 📋 Prérequis
- Compte Render (render.com)
- Compte GitHub avec le repo Brasil Burger poussé
- Compte Neon (pour la base de données PostgreSQL)

## 🚀 Étapes de déploiement

### 1. Préparer le code
✅ Déjà fait! Les fichiers suivants sont configurés:
- `appsettings.Production.json` - Configuration de production
- `render.yaml` - Configuration automatique de Render
- `Program.cs` - Support des variables d'environnement

### 2. Pousser le code sur GitHub
```bash
git init
git add .
git commit -m "Deploy to Render"
git branch -M main
git remote add origin https://github.com/VOTRE_USERNAME/brasil-burger.git
git push -u origin main
```

### 3. Se connecter à Render
1. Aller sur https://render.com
2. Se connecter ou créer un compte
3. Cliquer sur "New +" → "Web Service"
4. Sélectionner "Deploy an existing repository"
5. Connecter votre compte GitHub
6. Sélectionner le repo `brasil-burger`

### 4. Configuration Render
1. Dans "Service Settings":
   - **Name**: brasil-burger
   - **Environment**: Use render.yaml
   - **Branch**: main
   - **Build Command**: `dotnet build BrasilBurgerC.csproj`
   - **Start Command**: `dotnet run --project BrasilBurgerC.csproj --launch-profile Production`

2. **Environment Variables**:
   - `ASPNETCORE_ENVIRONMENT`: Production
   - `ASPNETCORE_URLS`: http://0.0.0.0:$PORT

3. **Plan**: Choisir Free (gratuit avec limitations) ou payant

### 5. Configurer la base de données Neon
1. Aller sur https://neon.tech
2. Copier la **CONNECTION STRING** de votre base de données Neon
3. Dans Render:
   - Cliquer sur "Environment" 
   - Ajouter variable: `DATABASE_URL` = votre connection string Neon
   - Format: `postgresql://user:password@host/dbname?sslmode=require`

### 6. Déployer
1. Cliquer sur "Create Web Service"
2. Render va automatiquement:
   - Cloner votre repo
   - Installer les dépendances
   - Compiler l'application
   - Lancer le service

### 7. Vérifier le déploiement
1. Attendre 3-5 minutes (premier déploiement plus long)
2. Une URL `https://brasil-burger-xxxxx.onrender.com` sera générée
3. Ouvrir l'URL pour tester

## 🔗 Vérifier les logs
- Dans Render: Dashboard → brasil-burger → "Logs"
- Voir les erreurs ou logs en temps réel

## 🔄 Redéployer
- Pusher les changements sur GitHub (branche main)
- Render redéploiera automatiquement

## ⚙️ Troubleshooting

### Erreur: "Failed to connect to database"
- Vérifier que la `DATABASE_URL` est correcte dans Render
- S'assurer que Neon a une base de données active
- Vérifier les logs Render

### Erreur: "Application won't start"
- Vérifier les logs: `dotnet run` doit fonctionner localement
- Compiler: `dotnet build`
- Vérifier que net8.0 est supporté

### Port configuré dynamiquement
- Render utilise la variable `$PORT` (port aléatoire)
- C'est déjà configuré dans `ASPNETCORE_URLS`

## 📦 Variables d'environnement importantes
```
ASPNETCORE_ENVIRONMENT=Production
ASPNETCORE_URLS=http://0.0.0.0:$PORT
DATABASE_URL=postgresql://user:password@host/dbname
```

## 🎯 Coûts
- **Free Tier Render**: Suffit pour un petit projet
- **Neon Free**: 3GB de stockage PostgreSQL

## 📝 Notes
- Sessions sont en mémoire (ASP.NET Core Sessions) - OK pour Render
- Images Unsplash se chargent directement (pas de stockage local)
- La base PostgreSQL Neon doit être accessible publiquement
