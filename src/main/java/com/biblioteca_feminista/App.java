package com.biblioteca_feminista;

import com.biblioteca_feminista.config.DBManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        DBManager.init();
        DBManager.close();
    }
}
