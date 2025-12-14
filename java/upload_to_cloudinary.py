import requests
import json
from urllib.parse import quote
import subprocess

# Cloudinary credentials
CLOUD_NAME = "dnw5idv6v"
API_KEY = "758272532167719"
API_SECRET = "eMAHHzcpnEG6nhP-A5_AwFWwtXU"

# Images to upload
images = {
    "coca": "https://i0.wp.com/commechezmams.fr/wp-content/uploads/2021/01/COCA-33cl.jpg?fit=800%2C800&ssl=1",
    "sauce": "https://images.unsplash.com/photo-1596040994633-923ee6c90785?w=400&h=300&fit=crop"
}

# Upload endpoint
url = f"https://api.cloudinary.com/v1_1/{CLOUD_NAME}/image/upload"

sql_updates = []

for name, image_url in images.items():
    print(f"Uploading {name}...")
    
    # Prepare upload data
    data = {
        "file": image_url,
        "api_key": API_KEY,
        "public_id": f"brasilburger_{name}"
    }
    
    try:
        # Upload to Cloudinary
        response = requests.post(url, data=data)
        response.raise_for_status()
        result = response.json()
        
        if "secure_url" in result:
            cloudinary_url = result["secure_url"]
            print(f"✅ {name} uploaded: {cloudinary_url}")
            
            # Prepare SQL update
            if name == "coca":
                sql_updates.append(f"UPDATE complements SET image_url = '{cloudinary_url}' WHERE LOWER(nom) IN ('coco', 'boisson gazeuse');")
            elif name == "sauce":
                sql_updates.append(f"UPDATE complements SET image_url = '{cloudinary_url}' WHERE LOWER(nom) LIKE '%sauce%';")
        else:
            print(f"❌ Error uploading {name}: {result}")
    except Exception as e:
        print(f"❌ Error uploading {name}: {e}")

# Write SQL file
if sql_updates:
    with open("update_cloudinary_images.sql", "w") as f:
        f.write("\n".join(sql_updates))
    print(f"\n✅ SQL file created: update_cloudinary_images.sql")
    
    # Execute SQL updates
    print("\nUpdating database...")
    cmd = f'psql -h ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech -U neondb_owner -d neondb -f update_cloudinary_images.sql'
    result = subprocess.run(cmd, shell=True, capture_output=True, text=True)
    print(result.stdout)
    if result.stderr:
        print("Errors:", result.stderr)
else:
    print("No images to update")
