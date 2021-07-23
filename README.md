# Publish Subscribe

* Compile os arquivos:

```bash
    javac -d classes Cell.java Client.java ClientImpl.java Server.java ServerImpl.java
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
    java -classpath classes -Djava.rmi.server.codebase=file:classes/ example.hello.Client
```