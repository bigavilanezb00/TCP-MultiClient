package com.example.tcpmulticlient.TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TCPClientObjLlista extends Thread {
    private String hostname;
    private int port;
    private boolean acabat = false;
    private InputStream is;
    private OutputStream os;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private Llista llista;

    public TCPClientObjLlista(String hostname, int port, Llista llista) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void run() {
        Socket socket;
        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            os = socket.getOutputStream();
            output = new ObjectOutputStream(os);
            is = socket.getInputStream();
            input = new ObjectInputStream(is);

            while (!acabat) {
                Llista llista = new Llista("llista1", Arrays.asList(10,2,45,2,67,10));
                List<Integer> llistaList = Arrays.asList(1,2,3,4,5,6,7,8);
                output.writeObject(llista);
                output.writeObject(llistaList);
                output.flush();

                llista = (Llista) input.readObject();
                printLlista(llista);
                acabat = true;
            }
        }catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
    private void printLlista(Llista llista) {
        llista.getNumberList().forEach(System.out::println);
    }

    public static void main(String[] args) {
        String jugador, ipSrv;

        Scanner sip = new Scanner(System.in);

        System.out.print("Ip del servidor: ");
        ipSrv = sip.next();
        System.out.print("Nom jugador: ");
        jugador = sip.next();

        System.out.println();

        List<Integer> integerArrayList = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            integerArrayList.add(((int) (Math.random() * 50))+1);
        }

        Llista llista = new Llista(jugador,integerArrayList);

        TCPClientObjLlista tcpClientObjLlista = new TCPClientObjLlista(ipSrv,2828, llista);
        tcpClientObjLlista.llista.setNom(jugador);
        tcpClientObjLlista.start();
    }
}
