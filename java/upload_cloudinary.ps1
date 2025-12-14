# Cloudinary credentials
$cloudName = "dnw5idv6v"
$apiKey = "758272532167719"
$apiSecret = "eMAHHzcpnEG6nhP-A5_AwFWwtXU"

# Generate upload signature
$timestamp = [int](Get-Date -UFormat %s)

# Image URLs
$images = @{
    "coca" = "https://i0.wp.com/commechezmams.fr/wp-content/uploads/2021/01/COCA-33cl.jpg?fit=800%2C800&ssl=1"
    "sauce" = "https://images.unsplash.com/photo-1596040994633-923ee6c90785?w=400&h=300&fit=crop"
}

$uploadUrl = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"

foreach ($key in $images.Keys) {
    $imageUrl = $images[$key]
    
    # Create signature
    $stringToSign = "file=$imageUrl&public_id=brasilburger_$key&timestamp=$timestamp&api_key=$apiKey"
    $bytes = [System.Text.Encoding]::UTF8.GetBytes($stringToSign + $apiSecret)
    $sha1 = [System.Security.Cryptography.SHA1]::Create()
    $hash = [System.Convert]::ToBase64String($sha1.ComputeHash($bytes))
    
    Write-Host "Uploading $key..."
    
    $body = @{
        file = $imageUrl
        api_key = $apiKey
        timestamp = $timestamp
        signature = $hash
        public_id = "brasilburger_$key"
    }
    
    try {
        $response = Invoke-WebRequest -Uri $uploadUrl -Method Post -Body $body
        Write-Host "✅ $key uploaded successfully"
        Write-Host $response.Content
    }
    catch {
        Write-Host "❌ Error uploading $key : $_"
        Write-Host $_.Exception.Response.Content
    }
}
