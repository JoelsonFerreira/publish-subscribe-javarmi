# Publish Subscribe

* Compile os arquivos:

```bash
    javac -d classes Client.java ClientInterface.java Server.java ServerInterface.java Cell.java
```

* Entre na pasta classes e execute RMIRegistry:

```bash
    cd classes
    rmiregistry &
    # Windows: start rmiregistry
```

* Na pasta hello execute o servidor:

```bash
    java -classpath classes -Djava.rmi.server.codebase=file:classes/ example.hello.Server
```

* Na pasta hello, em um novo terminal, execute o cliente:

```bash
    java -classpath classes -Djava.rmi.server.codebase=file:classes/ example.hello.Client [name]
```
