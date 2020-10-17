# Lab1.1 - Maven


## Instalação
    Para poder instalar o  Maven, o primeiro requisito é ter o Java JDK, tal pode ser verificado correndo o seguinte comando:
        $java --version

    De seguida, extrair o Apache Maven Binary Code (ambiente Linux):
        1. $sudo wget http://www-us.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz 
        2. $sudo tar -xf apache-maven-3.6.3-bin.tar.gz
        3. $sudo mv apache-maven-3.6.3/ apache-maven/ 
        
    Por fim, adicionar as variáveis de ambiente, correr o ficheiro de configuração 'maven.sh' e verificar a instalação:
        4. $sudo cd /etc/profile.d/
        5. $sudo nano maven.sh
       (adicionar ao ficheiro) export PATH=usr/local/src/apache-maven/bin:${PATH}
        6. $sudo chmod +x maven.sh
        7. $sudo source /etc/profile.d/maven.sh
        8. $mvn --version

## Maven in 5 Minutes:
    Tutorial em  https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

    Criar um Projeto (my-app):
    Correndo o seguinte commando resultará na craiação de um novo diretório denominado 'my-app'
        $mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
    Build do Projeto:
         $mvn package
    Correr o Projeto:
        $java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App
   
    
    Notas Importantes:
        "The src/main/java directory contains the project source code, the src/test/java directory contains the test source, and the pom.xml file is the project's Project Object Model, 
        or POM"
        pom.xml - Ficheiro de configuração de um projeto maven, contendo toda a informação requirida para fazer 'build' de um projeto da forma que pretendemos.


    Convenções de Nomeamento - Qual a função por trás do groupid,artifactid e archetype?:
        groupid - Identifica o projeto em todos os projetos Java criados, deverá seguir as convenções do Java para Package Naming, apesar de nao ser obrigatório.
       

        artifactid- Nome do ficheiro .jar sem especificar a versão. A única regra na nomeação destes ficheiros é que devem comecar sempre por letra minuscula e não conter símbolos "estranhos".

        archetype - é um toolkit de modelagem de projetos Maven definido como um padrão ou modelo original a partir da qual todas as outras coisas do mesmo tipo são feitas. 
        O nome encaixa-se já que estamos a tentar fornecer um sistema que forneça um meio consistente de gerar projetos Maven. Em suma, basicamente pode ajuda Developers a rapidamente 
        criar um projeto usando as melhores praticas especificadas por um certo archetype, seguindo um modelo.
        
       

   



## Preparar um novo projeto Maven(aplicação para consulta da Meteorologia - MyWeatherRadar):
    Definir versão 11 do compilador no ficheiro pom.xml (https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-source-and-target.html): 
        <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        </properties>
    Configurar Dependências:
        Biblioteca Retrofit, que nos irá permitir fazer um pedido GET à API do IPMA (https://square.github.io/retrofit/)
        Biblioteca GSON: com o intuito de fazer Parsing ao JSON que conseguimos ir buscar com a chamada da API. (https://github.com/google/gson/)
        Passos da Configuração em  https://www.baeldung.com/retrofit

    Correr o Projeto:
        $mvn clean package 
        $mvn exec:java -Dexec.mainClass="com.MyWeatherRadar.App"  



## Maven Build LifeCycle
    http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html

    "Although hardly a comprehensive list, these are the most common default lifecycle phases executed.

        >validate: validate the project is correct and all necessary information is available
        >compile: compile the source code of the project
        >test: test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
        >package: take the compiled code and package it in its distributable format, such as a JAR.
        >integration-test: process and deploy the package if necessary into an environment where integration tests can be run
        >verify: run any checks to verify the package is valid and meets quality criteria
        >install: install the package into the local repository, for use as a dependency in other projects locally
        >deploy: done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects."

    There are two other Maven lifecycles of note beyond the default list above. They are

        clean: cleans up artifacts created by prior builds ($mvn-clean)

        site: generates site documentation for this project($mvn site) 
    
        This phase generates a site based upon information on the project's pom. You can look at the documentation generated under target/site."

## Maven Site:
        Para gerar um site com a documentação do projeto, devemos correr o comando enunciado anteriormente:
            $mvn site
        Contudo, caso pretendamos adicionar documentação neste site acerca do código Java presente no projeto, dever-se-à seguir o tutorial no seguinte link:
            https://maven.apache.org/plugins/maven-javadoc-plugin/usage.html


## O que é um Maven Goal?
        Maven Goals - representam uma tarefa específica que contribui para a construção e gestão de um projeto, o que significa que uma Build Phase ( já referidas anteriormente neste documento ) é feita através de um conjunto de Maven Goals.
        Exemplo de Maven Goals e respetivas Build Phases à qual estão associados:

                Phase 	                    plugin:goal
            
            process-resources       	resources:resources
            compile 	                compiler:compile
            process-test-resources 	    resources:testResources
            test-compile 	            compiler:testCompile
            test 	                    surefire:test
            package 	                jar:jar
            install 	                install:install
            deploy 	                    deploy:deploy
        
        Contudo é importante referir que por vezes há goals que nao precisam de estar associados a uma build phase (podem ser corridos através de command line)

## Qual a sequência de evocação dos Maven Goals?
        Quando corremos uma Phase todos os goals associados a esta são executados em ordem, sequencialmente e de forma fixa