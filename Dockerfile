FROM mcr.microsoft.com/dotnet/sdk:8.0 as build
WORKDIR /src

COPY ["BrasilBurgerC.csproj", "./"]
RUN dotnet restore "BrasilBurgerC.csproj"

COPY . .
RUN dotnet build "BrasilBurgerC.csproj" -c Release -o /app/build
RUN dotnet publish "BrasilBurgerC.csproj" -c Release -o /app/publish

FROM mcr.microsoft.com/dotnet/aspnet:8.0
WORKDIR /app
COPY --from=build /app/publish .

EXPOSE 10000
ENV ASPNETCORE_ENVIRONMENT=Production

ENTRYPOINT ["dotnet", "BrasilBurgerC.dll"]
