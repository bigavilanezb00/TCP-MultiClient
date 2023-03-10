package com.example.tcpmulticlient.TCP.Tasca2;

import java.io.*;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ThreadSevidorObjLLista implements Runnable{

    private Socket clientSocket;
    private OutputStream os;
    private ObjectOutputStream output;
    private InputStream is;
    private ObjectInputStream input;
    private boolean acabat = false;


    public ThreadSevidorObjLLista(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            is = clientSocket.getInputStream();
            input = new ObjectInputStream(is);
            os = clientSocket.getOutputStream();
            output = new ObjectOutputStream(os);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!acabat) {
                //LLegim l'objecte LLista del stream input
                Llista llista = (Llista) input.readObject();

                //lectura i print de l'objecte List que es pot passar com a Object --> List és un objecte serialitzat
                List<Integer> llistaList = (List<Integer>) input.readObject();
                llistaList.forEach(System.out::println);

                //ordenem la llista i eliminem duplicats
                ordenarInetejar(llista);
                printLlista(llista);

                //tornem la llista al client per l'output
                output.writeObject(llista);
                output.flush();

                acabat = true;
            }
        }catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        try {
            output.close();
            input.close();
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Llista ordenarInetejar(Llista ll) {
        Collections.sort(ll.getNumberList());
        Set<Integer> unics = new HashSet<>(ll.getNumberList());
        ll.setNumberList(unics.stream().collect(Collectors.toList()));
        return ll;
    }

    private void printLlista(Llista llista) {
        llista.getNumberList().forEach(System.out::println);
    }
}
