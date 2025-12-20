FROM mcr.microsoft.com/dotnet/sdk:8.0 as build
WORKDIR /src

COPY ["BrasilBurgerC/BrasilBurgerC.csproj", "BrasilBurgerC/"]
RUN dotnet restore "BrasilBurgerC/BrasilBurgerC.csproj"

COPY . .
RUN dotnet build "BrasilBurgerC/BrasilBurgerC.csproj" -c Release -o /app/build

FROM mcr.microsoft.com/dotnet/aspnet:8.0
WORKDIR /app
COPY --from=build /app/build .

EXPOSE 8080
ENV ASPNETCORE_URLS=http://+:8080
ENV ASPNETCORE_ENVIRONMENT=Production

ENTRYPOINT ["dotnet", "BrasilBurgerC.dll"]
