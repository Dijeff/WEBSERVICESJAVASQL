/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaqueteWS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author berna
 */
@WebService(serviceName = "WebServicesGlobalWay")
public class WebServicesGlobalWay {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
     /**
     * Web service operation
     */
    @WebMethod(operationName = "CambioMonedaWS")
    public double CambioMonedaWS(@WebParam(name = "Pais") String Pais) {

        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        String connectionUrl = "jdbc:sqlserver://DESKTOP-FMDDBT7:1433;"
                + "databaseName=GlobalWay; user=sa; password=1234";

        double valormoneda = 0;
        String nombrepa = null;
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            cn = DriverManager.getConnection(connectionUrl);
            st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            rs = st.executeQuery("Select ValorMoneda, NombrePais from Paises ");

            while (rs.next()) {
                if (Pais.equals(rs.getString(2))) {
                    nombrepa = rs.getString(2);
                    valormoneda = rs.getDouble(1);
                }
            }
            cn.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return valormoneda;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GlobalWayReserva")
    public String GlobalWayReserva(@WebParam(name = "Pasaporte") int Pasaporte,
            @WebParam(name = "ID") int ID,
            @WebParam(name = "MonedaNecesito") String MonedaNecesito,
            @WebParam(name = "OficinaPagar") String OficinaPagar,
            @WebParam(name = "OficinaRecibir") String OficinaRecibir,
            @WebParam(name = "CantidadMoneda") int CantidadMoneda) {

        try {
            Connection cn = null;
            Statement st = null;
            ResultSet rs = null;
            String connectionUrl = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=GlobalWay; user=provedor; password=12345sa";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            cn = DriverManager.getConnection(connectionUrl);
            st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            String insertar = "INSERT INTO Reservaciones VALUES (?,?, ?, ?, ?,?, getdate())";
            // the mysql insert statement

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = cn.prepareStatement(insertar);

            preparedStmt.setInt(1, Pasaporte);
            preparedStmt.setInt(2, ID);
            preparedStmt.setInt(3, CantidadMoneda);
            preparedStmt.setString(4, MonedaNecesito);
            preparedStmt.setString(5, OficinaPagar);
            preparedStmt.setString(6, OficinaRecibir);
            preparedStmt.execute();

            rs = st.executeQuery("Select Pasaporte,Cedula,Nombre_Apellidos, Correo from ClientesUsers ");
            String Nombre = null, Correo = null;
            boolean existe = false;
            while (rs.next()) {
                if (Pasaporte == rs.getInt(1) && ID == rs.getInt(2)) {
                    Nombre = rs.getString(3);
                    Correo = rs.getString(4);
                    existe = true;
                }
            }
            if (existe) {
                ClaseCorreo correo = new ClaseCorreo();
                correo.mandarCorreoATodos(Correo, Nombre, OficinaPagar, OficinaRecibir, CantidadMoneda, MonedaNecesito);
            }

            cn.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "Todo Salio Bien";
    }

    @WebMethod(operationName = "comision")
    public String Comision(@WebParam(name = "name") String moneda, double monto) {
        double comision = 0;
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            String connectionUrl = "jdbc:sqlserver://DESKTOP-FMDDBT7:1433;"
                    + "databaseName=GlobalWay; user=sa; password=1234";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            cn = DriverManager.getConnection(connectionUrl);
            st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            rs = st.executeQuery("Select PorcentajeComi from Paises where Moneda='" + moneda + "'");
            while (rs.next()) {
                comision = rs.getDouble(1);

            }
            comision = monto * comision;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "La comision de el cambio de  " + monto + " " + moneda + " es " + comision + " " + moneda;
    }
@WebMethod(operationName = "Bank")
    public String Bank(@WebParam(name = "Tarjeta") int Tarjeta, @WebParam(name = "Token") int Token, @WebParam(name = "Nombre") String Nombre, @WebParam(name = "Monto") int Monto, @WebParam(name = "Fecha") String Fecha) {
        String Respuesta = "El pago del monto "+ Monto +" de la tarjeta: "+ Tarjeta + " perteneciente a " + Nombre + " se realizo con exito en la fecha " + Fecha;
        return Respuesta;
    }
}
