package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by bleizingard on 25/01/18.
 */

public abstract class DAOMySql<T>
{
	/* Déclaration & Initialisation de la connexion */
	private String		URL		= "jdbc:mysql://localhost:3306/DAO";
	private String		USER	= "root";
	private String		PSW		= "";
	protected Connection	cnx;
	protected Statement	stmt;
	protected ResultSet	rs;

	public void DAOMySql(String server_address, String port, String user, String psw, String database)
	{
		this.URL = "jdbc:mysql://" + server_address + ":" + port + "/" + database;
		this.USER = user;
		this.PSW = psw;
	}

	protected boolean connexion()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			cnx = DriverManager.getConnection(URL, USER, PSW);
			stmt = cnx.createStatement();

		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Problème de driver");
			e.printStackTrace();
			return false;
		}
		catch (SQLException e)
		{
			System.out.println("Erreur de connexion à la base de données");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	protected boolean deconnexion()
	{

		try
		{
			this.stmt.close();
			this.cnx.close();
		}
		catch (SQLException e)
		{
			System.out.println("Erreur de connexion à la base de données");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	//public abstract T getInstanceDAO();

	public abstract boolean add(T o) throws Exception;
	public abstract boolean update(T o) throws Exception;
	public abstract boolean delete(T o) throws Exception;
	public abstract ArrayList<T> getAll() throws Exception;
	public abstract ArrayList<T> getAll(String clause, String value) throws Exception;
	public abstract T get(int id) throws Exception;
}
