docker build -t jeffersonfarias/catalogo-dados-batch:1.0.0  -f Dockerfile .
docker login --username=jeffersonfarias
docker push jeffersonfarias/catalogo-dados-batch:1.0.0
