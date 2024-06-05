Generate key pair

openssl genrsa -out keypair.pem 2048

Create public key

openssl rsa -in keypair.pem -pubout -out public.pem

Extract private key

openssl rsa -in keypair.pem -pubout -out public.pem