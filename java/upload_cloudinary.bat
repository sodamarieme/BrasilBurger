@echo off
REM Cloudinary credentials
set CLOUD_NAME=dnw5idv6v
set API_KEY=758272532167719

echo Uploading Coca image...
curl -X POST ^
  -F "file=https://i0.wp.com/commechezmams.fr/wp-content/uploads/2021/01/COCA-33cl.jpg?fit=800%%2C800%%26ssl=1" ^
  -F "api_key=%API_KEY%" ^
  -F "public_id=brasilburger_coca" ^
  https://api.cloudinary.com/v1_1/%CLOUD_NAME%/image/upload ^
  > coca_response.json

echo Uploading Sauce image...
curl -X POST ^
  -F "file=https://images.unsplash.com/photo-1596040994633-923ee6c90785?w=400&h=300&fit=crop" ^
  -F "api_key=%API_KEY%" ^
  -F "public_id=brasilburger_sauce" ^
  https://api.cloudinary.com/v1_1/%CLOUD_NAME%/image/upload ^
  > sauce_response.json

echo Done. Check coca_response.json and sauce_response.json for results.
