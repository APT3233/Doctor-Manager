import gzip
import base64
from io import BytesIO
import hashlib

def hash_string(input_string):
    input_bytes = input_string.encode('utf-8')
    hash_object = hashlib.sha256()
    hash_object.update(input_bytes)

    hash_hex = hash_object.hexdigest()
    
    return hash_hex

print(" ------------- Gen Key -------------")
hashed_string = hash_string(input("Username: "))
hash_string_2 = hash_string(input("Password: "))
print("Hashed user:", hashed_string)
print("Hashed Pass: ", hash_string_2)
print("---------------- End ----------------")
