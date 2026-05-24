#!/usr/bin/env bash
set -e

LOCAL_CONF="local.conf"
LOCAL_KEY="local.key"
PEM_FILE="MyLocalCA.pem"
KEY_FILE="MyLocalCA.key"
SRL_FILE="MyLocalCA.srl"
CSR_FILE="local.csr"
CRT_FILE="local.crt"
PASSWORD="secret_password"
P12="riddler.urpi.be.p12"
TARGET_DIR="../ui/src/main/resources"

function clean() {
    if [ -f "$LOCAL_KEY" ]; then rm "$LOCAL_KEY"; fi
    if [ -f "$CSR_FILE" ]; then rm "$CSR_FILE"; fi
    if [ -f "$CRT_FILE" ]; then rm "$CRT_FILE"; fi
#    if [ -f "$PEM_FILE" ]; then rm "$PEM_FILE"; fi
    if [ -f "$KEY_FILE" ]; then rm "$KEY_FILE"; fi
    if [ -f "$SRL_FILE" ]; then rm "$SRL_FILE"; fi
}

# Clean old artifacts
clean
if [ -f "$P12" ]; then rm "$P12"; fi
if [ -f "$TARGET_DIR/$P12" ]; then rm "$TARGET_DIR/$P12"; fi

echo "--> Generating Root CA..."
openssl req -x509 -new -nodes -newkey rsa:2048 -keyout "$KEY_FILE" -sha256 -days 1825 -out "$PEM_FILE" -subj "/C=BE/O=Riddler/CN=Local Root CA"

echo "--> Creating CSR and Key..."
openssl req -new -nodes -out "$CSR_FILE" -newkey rsa:2048 -keyout "$LOCAL_KEY" -config "$LOCAL_CONF"

echo "--> Signing Certificate with Local CA..."
openssl x509 -req -in "$CSR_FILE" \
  -CA "$PEM_FILE" \
  -CAkey "$KEY_FILE" \
  -CAcreateserial \
  -out "$CRT_FILE" \
  -days 825 \
  -sha256 \
  -extensions v3_ca_sign \
  -extfile "$LOCAL_CONF"

echo "--> Creating PKCS12 Keystore Bundle..."
openssl pkcs12 -export \
  -in "$CRT_FILE" \
  -inkey "$LOCAL_KEY" \
  -out "$P12" \
  -name "riddler.urpi.be" \
  -password "pass:$PASSWORD"

echo "--> Deploying Keystore Asset..."
mkdir -p "$TARGET_DIR"
mv "$P12" "$TARGET_DIR/$P12"

echo "--> Success! Key deployed to $TARGET_DIR/$P12"
clean
