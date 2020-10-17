# IES_Lab1.2 - Git

Na pasta, MyWeatherRadar encontra-se o projeto do lab1.1 complementado com os exercícios do lab1.2.
No repositorio GitHub https://github.com/DanielGomes14/IES_Lab1.git, inicialmente foi apenas utilizado para a elaboração do lab1.2 pelo que lab1-2.log contém apenas essas operações de git, 
contudo todas as alterações posteriores apenas tiveram em vista colocar todo o trabalho efetuado no lab1 no rep.

## Fluxo de trabalho em Git:

    Criar um repositório Remoto (GitHub, no meu caso)
    Initialize repo, ignorar pasta "/target" e fazer push das alterações:
       $ git init  
       $ echo "#IES_Lab1" >> README.md
       $ git add .
       $ git rm -- cached MyWeatherRadar/target
       $ echo "target/" >> .gitignore
       $ git commit -m "first commit"
       $ git branch -M main
       $ git remote add origin https://github.com/DanielGomes14/IES_Lab1.git
       $ git push -u origin main


Após a criação do repositório, eu e o Leandro Silva procedemos a realização das features 1 e 2, tal como eram propostas no enunciado deste guião.A
Assim, cada um de nós criou uma branch (daniel-feature,leand), estando eu responsável pela feature 2 enquanto que o Leandro responsável pela feature 1.
Muitos dos  comandos usados, encontram-se neste site: https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow

Comandos fundamentais associados a branches que nos auxiliaram( `branch-name` corresponde ao nome da branch em causa):

    Criar uma branch:
        $ git checkout -b branch-name
    Mudar de branch:
        $ git checkout branch-name
    Observar em que branch estamos a trabalhar e que ficheiros estão por adicionar, entre outras informações
        $ git status
    Fazer push das alterações para a branch remota :
        $ git push -u origin branch-name

O ficheiro lab1-2.log contém o registo das operações git realizadas até ao fim das 2 features, esse ficheiro foi criado utilizando o seguinte comando:

        $ git log --graph --oneline --decorate> lab1-2.log
