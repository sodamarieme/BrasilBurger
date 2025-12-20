# Build stage
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /app

# Copy the project files
COPY BrasilBurgerC/BrasilBurgerC.csproj ./BrasilBurgerC/
COPY . .

# Restore dependencies
WORKDIR /app/BrasilBurgerC
RUN dotnet restore

# Build the application
RUN dotnet build -c Release --no-restore

# Runtime stage
FROM mcr.microsoft.com/dotnet/aspnet:8.0
WORKDIR /app

# Copy build output
COPY --from=build /app/BrasilBurgerC/bin/Release/net8.0 .

# Expose port
EXPOSE 8080

# Environment
ENV ASPNETCORE_ENVIRONMENT=Production
ENV ASPNETCORE_URLS=http://+:8080

# Start the application
ENTRYPOINT ["dotnet", "BrasilBurgerC.dll"]
