# governanca-dados-batch

criar um diretorio ~/input/

criar um diretorio ~/input/erro

criar um diretorio ~/input/processado

cd ~/input/

#executar

docker run --name catalogo-dados-batch -v $PWD:/app/input --link elasticsearch  --network somenetwork  -p 8099:8099 -d jeffersonfarias/catalogo-dados-batch:1.0.0


sudo docker exec -i -t catalogo-dados-batch sh




