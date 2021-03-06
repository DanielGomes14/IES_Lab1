# IES_Lab1.3 - Introdução ao Docker

Instalação do Docker :
    Tutorial em  https://docs.docker.com/engine/install/

    Verificar instalação:
        $sudo docker run hello-world
        OU
        $docker --version
    
    Gerir Docker como non-root user:
        $sudo groupadd docker
        $sudo gpasswd -a $USER docker


## Build and run your image:
Info em https://docs.docker.com/get-started/part2/

    Introdução:
        "Now that you’ve set up your development environment, you can begin to develop containerized applications. In general, the development workflow looks like this:

        Create and test individual containers for each component of your application by first creating Docker images.

        Assemble your containers and supporting infrastructure into a complete application.

        Test, share, and deploy your complete containerized application.
        In this stage of the tutorial, let’s focus on step 1 of this workflow: creating the images that your containers will be based on. Remember, a Docker image captures the private filesystem that your containerized processes will run in; you need to create an image that contains just what your application needs to run."

    Definir um container com Dockerfile:
        Fazer clone do rep node-bulletin-board do GitHub:

            $git clone https://github.com/dockersamples/node-bulletin-board
            $cd node-bulletin-board/bulletin-board-app   
        DockerFile:

            "Dockerfiles describe how to assemble a private filesystem for a container, and can also contain some metadata describing how to run a container based on this image", funcionam como dependências para conseguirmos fazer build e correr esta app

        Fazer Build da Imagem:
            $docker build --tag bulletinboard:1.0 .
        Correr a Imagem como um Container (uma instância de uma imagem basicamente é um container) :
        
            $docker run --publish 8000:8080 --detach --name bb bulletinboard:1.0
            "--publish asks Docker to forward traffic incoming on the host’s port 8000 to the container’s port 8080. Containers have their own private set of ports, so if you want to reach one from the network, you have to forward traffic to it in this way. Otherwise, firewall rules will prevent all network traffic from reaching your container, as a default security posture.
            --detach asks Docker to run this container in the background.
            --name specifies a name with which you can refer to your container in subsequent commands, in this case bb."

        Parar container de correr:
            $docker stop bb

        Eliminar container:
            docker rm --force bb

## Alguns comandos do Docker úteis:

    Executar uma Docker Image(já visto antes):
        $docker run image-name
    Remove imagem :
        $docker image rm image-id
    Remover todas as imagens:
        $docker image rm $(docker image ls -a -q)
    Remover container:
        #docker container rm container-name
    Parar container:
        $docker container stop container-name
    Listar comandos:
        $docker --help
    Listar imagens :
        $docker image ls
    Listar containers:
        $docker container ls
    Listar todos os processos ativos:
        $docker ps
    https://stackoverflow.com/questions/45254677/is-there-a-difference-between-docker-ps-and-docker-container-ls


## Portainer com Docker:

    Info em https://www.portainer.io/installation/
    Fazer deployment de um servidor Portainer num standalone host(Linux):

        $docker volume create portainer_data
        $docker run -d -p 8000:8000 -p 9000:9000 --name=portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce

## Dockerize PostgreSQL:

    Info em https://docs.docker.com/engine/examples/postgresql_service/
    Criar DockerFile (encontra-se dentro da pasta "post")
    
    Fazer build da imagem da dockerfile e atribuir um nome:

        (no meu caso deu erro de instalação por causa de umas keys, contudo voltei a correr o comando mais 2 vezes e funcionou)
        $docker build -t eg_postgresql .
        "Note: The --rm removes the container and its image when the container exits successfully."

    Correr Container:

        $docker run --rm -P --name pg_test eg_postgresql
    Fazer Container Linking:

        $docker run --rm -t -i --link pg_test:pg eg_postgresql bash
        postgres@7ef98b1b7243:/$ psql -h $PG_PORT_5432_TCP_ADDR -p $PG_PORT_5432_TCP_PORT -d docker -U docker --password

    Conectar:

        $psql -h localhost -p 49153 -d docker -U docker --password
    Testar DB

Para conseguir persistir os dados da base de dados, teríamos de criar um volume e fazer link a esta imagem, usar `docker-compose` por exemplo. Também o podemos fazer recorrendo ao portainer,que o faz automaticamente. Uma outra opção para utilizar volumes será, seguindo a configuração atual da dockerfile existente, recorrer à flag `-v`, ao correr o container:
    
    $ docker run --rm -v pgdata:/var/lib/postgresql/9.3/main -P --name pg_test eg_postgresql



## Docker Compose:
Todo o projeto seguido do tutorial da pagina https://docs.docker.com/compose/gettingstarted/ encontra-se na pasta composte-test. Basicamente com o docker compose podemos facilmente fazer build e correr uma stack de múltiplos containers, pelo que se todos os componentes correrem corretamente num container, conseguimos descrever toda a app como uma compose file.
Necessário instalar o docker composer:

        $sudo curl -L "https://github.com/docker/compose/releases/download/1.27.4/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
        $sudo chmod +x /usr/local/bin/docker-compose

### Comandos relevantes do Docker Compose:

    Começar aplicação:
    $ docker-compose up

    Começar aplicação em detached mode:
    $ docker-compose up -d

    Parar todos os serviços:
    $ docker-compose stop

    Remover todos os containers e até a data dos volumes:
    $ docker-compose down --volumes

## Qual a relevância do commando docker container ls --all?
Permite mostrar todos os containers docker criados, assim como os seu ID, data de criação, data de paragem do container, e nome associado. 

## Qual a relevância de configurar “volumes” quando se pretende preparar um container para servir uma base de dados?
Configurando volumes conseguimos fazer persistir os dados na DB, mesmo que apaguemos o container. Assim, permitimos que seja mais seguro fazer reboots do sistema, pois os dados irão se manter.
