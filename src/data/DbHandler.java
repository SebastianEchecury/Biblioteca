package data;

import entities.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class DbHandler {

	private String driver="com.mysql.cj.jdbc.Driver";
	private String host="localhost";
	private String port="3306";
	private String user="root";
	private String password="seba";
	private String db="biblioteca";
	
	
	private Connection conn=null;
	
	public void dbHandler() { 
		//registrar el driver de conexion Class.forName(...)
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Metodo para crear conexion
	private Connection getConnection() {
		try {
			if (conn== null || conn.isClosed())
			conn=DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+db, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return conn;
	}
	
	//Metodo para liberar conexion
	private void releaseConnection() {
		try {
			if(conn!=null && !conn.isClosed()) {
				conn.close();
			} 
		}catch (SQLException e) {
					e.printStackTrace();
			}
		}
	
		
	public int altaSocio(Socio s) {
		
		PreparedStatement pstmt=null;
		ResultSet keyRS = null;
		Connection conn = null;
		int id = 0;
		
		try {
			conn = this.getConnection();
			
			pstmt = conn.prepareStatement("insert into socios(apellido, nombre, mail, domicilio, telefono) values (?,?,?,?,?)",
						PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, s.getApellido());
			pstmt.setString(2, s.getNombre());
			pstmt.setString(3, s.getMail());
			pstmt.setString(4, s.getDomicilio());
			pstmt.setString(5, s.getTelefono());
			pstmt.executeUpdate();
			
			keyRS = pstmt.getGeneratedKeys();
			
			if(keyRS!=null && keyRS.next()) {
				id = keyRS.getInt(1);
			}
			
			return id;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		} finally {
			try {
				if(keyRS!=null){keyRS.close();}
	            if(pstmt!=null){pstmt.close();}
				this.releaseConnection();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	}

	public LinkedList<Socio> listarSocios() {
		
		Statement stmt=null;
		ResultSet rs = null;
		Connection conn = null;
		LinkedList<Socio> socios = new LinkedList<Socio>();
		
		try {
			conn=this.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from socios");
			
			while (rs!=null &&  rs.next()) {
				Socio s = new Socio();
				
				s.setIdSocio(rs.getInt("idsocio"));
				s.setApellido(rs.getString("apellido"));
				s.setNombre(rs.getString("nombre"));
				s.setDomicilio(rs.getString("domicilio"));
				s.setTelefono(rs.getString("telefono"));
				s.setMail(rs.getString("mail"));
				
				socios.add(s);
			}
		
			return socios;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally { 
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void bajaSocio(Socio s) {
		
		PreparedStatement pstmt=null ;
		Connection conn=null;
		
		try {
			conn=this.getConnection();
			pstmt=conn.prepareStatement("delete from socios where idsocio = ?");
			pstmt.setInt(1, s.getIdSocio());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	public Socio buscarSocio(Socio s) {
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Connection conn=null;
		
		try {
			Socio socio=null;
			conn=this.getConnection();
			pstmt=conn.prepareStatement("select * from socios where idsocio=?");
			pstmt.setInt(1, s.getIdSocio());
			rs=pstmt.executeQuery();
			
			if (rs!=null && rs.next()) {
				socio = new Socio();
				socio.setIdSocio(rs.getInt("idsocio"));
				socio.setApellido(rs.getString("apellido"));
				socio.setNombre(rs.getString("nombre"));
				socio.setDomicilio(rs.getString("domicilio"));
				socio.setMail(rs.getString("mail"));
				socio.setTelefono(rs.getString("telefono"));
			}
			
			return socio;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally { 
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void modSocio(Socio s) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update socios set apellido=?,nombre=?,mail=?,domicilio=?,telefono=? where idsocio = ?");
			pstmt.setString(1, s.getApellido());
			pstmt.setString(2, s.getNombre());
			pstmt.setString(3, s.getMail());
			pstmt.setString(4, s.getDomicilio());
			pstmt.setString(5, s.getTelefono());
			pstmt.setInt(6, s.getIdSocio());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}				
	}

	public int altaLibro(Libro l) {
		
		int id=0;
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet kRS = null;
		
		try {
			conn = this.getConnection();
			
			pstmt = conn.prepareStatement("insert into libros(titulo, isbn, nro_edicion, fecha_edicion, maxdias_prestamo) values (?,?,?,?,?)", 
					PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, l.getTitulo());
			pstmt.setString(2, l.getIsbn());
			pstmt.setString(3, l.getNroEdicion());
			pstmt.setObject(4, l.getFechaEdicion());
			pstmt.setInt(5, l.getMaxDiasPrestamo());
			
			pstmt.executeUpdate();
			kRS = pstmt.getGeneratedKeys();
			
            if(kRS!=null && kRS.next()) {
                id=kRS.getInt(1);
            }
            return id;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		} finally { 
			try {
				if(kRS!=null)kRS.close();
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	public LinkedList<Libro> listarLibros() {
		
		LinkedList<Libro> libros = new LinkedList<Libro>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from libros");
			
			while(rs!=null && rs.next()) {
				Libro l = new Libro();
				
				l.setIdLibro(rs.getInt("idlibro"));
				l.setIsbn(rs.getString("isbn"));
				l.setTitulo(rs.getString("titulo"));
				l.setNroEdicion(rs.getString("nro_edicion"));
				l.setFechaEdicion(rs.getObject("fecha_edicion", LocalDate.class));
				l.setMaxDiasPrestamo(rs.getInt("maxdias_prestamo"));
				
				libros.add(l);
			}
			
			return libros;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally { 
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	public Libro buscarLibro(Libro l) {

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select * from libros where idlibro = ?");
			pstmt.setInt(1, l.getIdLibro());
			rs = pstmt.executeQuery();
			
			if(rs!=null && rs.next()) {
				l.setIsbn(rs.getString("isbn"));
				l.setTitulo(rs.getString("titulo"));
				l.setNroEdicion(rs.getString("nro_edicion"));
				l.setFechaEdicion(rs.getObject("fecha_edicion", LocalDate.class));
				l.setMaxDiasPrestamo(rs.getInt("maxdias_prestamo"));
			}
			return l;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally { 
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
	}

	public void bajaLibro(Libro l) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("delete from libros where idlibro = ?");
			pstmt.setInt(1, l.getIdLibro());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	public void modLibro(Libro l) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update libros set isbn=?,titulo=?,nro_edicion=?,fecha_edicion=?,maxdias_prestamo=? where idlibro=?");
			pstmt.setString(1, l.getIsbn());
			pstmt.setString(2, l.getTitulo());
			pstmt.setString(3, l.getNroEdicion());
			pstmt.setObject(4, l.getFechaEdicion());
			pstmt.setInt(5, l.getMaxDiasPrestamo());
			pstmt.setInt(6, l.getIdLibro());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	public PoliticaPrestamo buscarPoliticaP(PoliticaPrestamo pp) {
		
		Statement stmt1 = null;
		Statement stmt2 = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();
			
			stmt1.execute("select max(fecha) into @maxi from politica_prestamo where fecha<=curdate()");
			rs = stmt2.executeQuery("select * from politica_prestamo where fecha=@maxi");
			
			if(rs!=null && rs.next()) {
				pp.setIdPolitica(rs.getInt("idpolitica"));
				pp.setFecha(rs.getObject("fecha", LocalDate.class));
				pp.setCantMaxLibrosPend(rs.getInt("cantmax_librospend"));
			}
			
			return pp;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally { 
			try {
				if(stmt1!=null)stmt1.close();
				if(stmt2!=null)stmt2.close();
				if(rs!=null)rs.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	public void altaPoliticaP(PoliticaPrestamo pp) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("insert into politica_prestamo (fecha, cantmax_librospend) values (?,?)");
			pstmt.setObject(1, pp.getFecha());
			pstmt.setInt(2, pp.getCantMaxLibrosPend());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	public LinkedList <PoliticaSancion> listarPoliticaS() {
		
		LinkedList <PoliticaSancion> politicas = new LinkedList<PoliticaSancion>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from politica_sancion");
			
			while(rs!=null && rs.next()) {
				PoliticaSancion ps = new PoliticaSancion();
				ps.setIdPolitica(rs.getInt("idpolitica"));
				ps.setDiasDesde(rs.getInt("dias_desde"));
				ps.setDiasHasta(rs.getInt("dias_hasta"));
				ps.setDiasSancion(rs.getInt("dias_sancion"));
				
				politicas.add(ps);
			}
			return politicas;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally { 
			try {
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	public int modPolitcaS(PoliticaSancion p) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update politica_sancion set dias_sancion = ? where idpolitica = ?");
			pstmt.setInt(1, p.getDiasSancion());
			pstmt.setInt(2, p.getIdPolitica());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	public int altaEjemplar(Libro libro, int cant) {

		PreparedStatement pstmt = null;
		Connection conn = null;
		int cambios = 0;

		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("insert into ejemplares (idlibro) values (?)");
			pstmt.setInt(1, libro.getIdLibro());
			
			for (int i = 1; i<=cant; i++) {
				cambios += pstmt.executeUpdate();
			}
			return cambios;
		} catch (SQLException e) {
			e.printStackTrace();
			return cambios;
		}finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	public boolean existeEjemplar(Ejemplar e) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		boolean existe = false;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select * from ejemplares where idejemplar = ?");
			pstmt.setInt(1, e.getIdEjemplar());	
			existe = pstmt.execute();
			return existe;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return existe;
		}finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
	}

	public void eliminarEjemplar(Ejemplar e) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("delete from ejemplares where idejemplar = ?");
			pstmt.setInt(1, e.getIdEjemplar());
			pstmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
	}

	public boolean estaSancionado(Socio s) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		boolean sancionado = false;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select * from sanciones where (fecha_sancion<=curdate() and DATE_ADD(fecha_sancion, INTERVAL dias_sancion DAY)>=curdate() and idsocio=?)");
			pstmt.setInt(1, s.getIdSocio());
			rs = pstmt.executeQuery();
			if(rs!=null && rs.next()) sancionado = true;
			return sancionado;
		} catch (SQLException e) {
			e.printStackTrace();
			return sancionado;
		}	finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public int cuantosPrestamos(Socio s) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int cantPrestamos=-1;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select count(*) from lineas_prestamo lp inner join prestamos p on lp.idprestamo=p.idprestamo where p.idsocio=? and lp.fecha_devolucion is null");
			pstmt.setInt(1, s.getIdSocio());
			rs = pstmt.executeQuery();
			
			if(rs!=null && rs.next()) {
				cantPrestamos = rs.getInt("count(*)");
			}
			return cantPrestamos;
		} catch (SQLException e) {
			e.printStackTrace();
			return cantPrestamos;
		}finally { 
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public LinkedList<Libro> librosRetiradosXSocio(Socio s) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		LinkedList<Libro> libros = new LinkedList<Libro>();
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select l.* "
										+ "from prestamos p"
										+ " inner join lineas_prestamo lp on lp.idprestamo=p.idprestamo"
										+ " inner join ejemplares e on e.idejemplar=lp.idejemplar"
										+ " inner join libros l on l.idlibro=e.idlibro"
										+ " where p.idsocio=? and lp.fecha_devolucion is null");
			pstmt.setInt(1, s.getIdSocio());
			rs = pstmt.executeQuery();
			
			while(rs!=null && rs.next()) {
				Libro l = new Libro();
				l.setIdLibro(rs.getInt("idlibro"));
				l.setIsbn(rs.getString("isbn"));
				l.setTitulo(rs.getString("titulo"));
				l.setNroEdicion(rs.getString("nro_edicion"));
				l.setFechaEdicion(rs.getObject("fecha_edicion", LocalDate.class));
				l.setMaxDiasPrestamo(rs.getInt("maxdias_prestamo"));
				
				libros.add(l);
			}
			return libros;
		} catch (SQLException e) {
			e.printStackTrace();
			return libros;
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public int buscarMaxCantDias(Ejemplar e) {

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int dias = 0;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select min(l.maxdias_prestamo) from libros l inner join ejemplares e on l.idlibro=e.idlibro where e.idejemplar=?");
			pstmt.setInt(1, e.getIdEjemplar());
			rs = pstmt.executeQuery();
			if(rs!=null && rs.next()) dias = rs.getInt("min(l.maxdias_prestamo)");
			return dias;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return dias;
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public int registrarPrestamo(Socio s, int dias) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet krs = null;
		int id = 0;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("insert into prestamos (fecha_prestamo, hora_prestamo, dias, idsocio) values(?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setObject(1, LocalDate.now());
			pstmt.setObject(2, LocalTime.now());
			pstmt.setInt(3, dias);
			pstmt.setInt(4, s.getIdSocio());
			pstmt.execute();
			krs = pstmt.getGeneratedKeys();
			if(krs!=null && krs.next()) {
				id = krs.getInt(1);
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				if(krs!=null)krs.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void registrarLineaPrestamo(int idPrestamo, Ejemplar e) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("insert into lineas_prestamo (idejemplar, idprestamo) values(?,?)");
			pstmt.setInt(1, e.getIdEjemplar());
			pstmt.setInt(2, idPrestamo);
			pstmt.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public boolean ejemplarPendiente(Ejemplar e) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		boolean pendiente = false;
		ResultSet rs = null;

		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select *"
										+ " from ejemplares e"
										+ " where e.idejemplar in"
										+ " (select lp.idejemplar"
										+ " from lineas_prestamo lp"
										+ " where lp.fecha_devolucion is null and lp.idejemplar=?)");
			pstmt.setInt(1, e.getIdEjemplar());
			rs = pstmt.executeQuery();
			if(rs!=null && rs.next()) pendiente = true;
			return pendiente;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return pendiente;
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public Socio buscarSocioXEjemplar(Ejemplar e) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			Socio s = null;
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select s.* "
										+ " from ejemplares e"
										+ " inner join lineas_prestamo lp on lp.idejemplar=e.idejemplar"
										+ " inner join prestamos p on p.idprestamo=lp.idprestamo"
										+ " inner join socios s on s.idsocio=p.idsocio"
										+ " where e.idejemplar=? and lp.fecha_devolucion is null");
			pstmt.setInt(1, e.getIdEjemplar());
			rs = pstmt.executeQuery();
			if(rs!=null && rs.next()) {
				s = new Socio();
				s.setIdSocio(rs.getInt("idsocio"));
				s.setApellido(rs.getString("apellido"));
				s.setNombre(rs.getString("nombre"));
				s.setDomicilio(rs.getString("domicilio"));
				s.setMail(rs.getString("mail"));
				s.setTelefono(rs.getString("telefono"));
			}
			return s;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		

	}

	public Libro buscarLibroXEjemplar(Ejemplar e) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			Libro l = null;
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select l.* from ejemplares e inner join libros l on e.idlibro = l.idlibro where e.idejemplar = ?");
			pstmt.setInt(1, e.getIdEjemplar());
			rs = pstmt.executeQuery();
			
			if (rs!=null && rs.next()) {
				l = new Libro();
				l.setIdLibro(rs.getInt("idlibro"));
				l.setIsbn(rs.getString("isbn"));
				l.setTitulo(rs.getString("titulo"));
				l.setNroEdicion(rs.getString("nro_edicion"));
				l.setFechaEdicion(rs.getObject("fecha_edicion", LocalDate.class));
				l.setMaxDiasPrestamo(rs.getInt("maxdias_prestamo"));
			}
			return l;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public LocalDate calcularFechaDevolucion(Ejemplar e) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		LocalDate fechaDevolucion = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select DATE_ADD(p.fecha_prestamo, INTERVAL p.dias DAY) fecdev"
					+ " from lineas_prestamo lp"
					+ " inner join prestamos p on p.idprestamo=lp.idprestamo"
					+ " where lp.idejemplar=?");
			pstmt.setInt(1, e.getIdEjemplar());
			rs = pstmt.executeQuery();
			
			if(rs!=null && rs.next()) {
				fechaDevolucion = rs.getObject("fecdev", LocalDate.class);
			}
			return fechaDevolucion;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return fechaDevolucion;
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				if(rs!=null)rs.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void registrarSancion(long dias, Socio s) {
		
		PreparedStatement pstmt1 = null, pstmt2 = null;
		Connection conn = null;
		
		try {
			conn = this.getConnection();
			
			pstmt1 = conn.prepareStatement("select  dias_sancion into @diass from politica_sancion where dias_desde<=? and dias_hasta>=?");
			pstmt1.setLong(1, dias);
			pstmt1.setLong(2, dias);
			pstmt1.execute();
			
			pstmt2 = conn.prepareStatement("insert into sanciones (fecha_sancion, dias_sancion, idsocio) values(?, @diass, ?)");
			pstmt2.setObject(1, LocalDate.now());
			pstmt2.setInt(2, s.getIdSocio());
			pstmt2.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			try {
				if(pstmt1!=null)pstmt1.close();
				if(pstmt2!=null)pstmt2.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public int registrarDevolucion(Ejemplar e) {
	
		PreparedStatement pstmt = null;
		Connection conn = null;
		int actualizo = 0;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update lineas_prestamo"
										+ " set fecha_devolucion = ?"
										+ " where idejemplar = ? and fecha_devolucion is null");
			pstmt.setObject(1, LocalDate.now());
			pstmt.setInt(2, e.getIdEjemplar());
			actualizo = pstmt.executeUpdate();
			return actualizo;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return actualizo;
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public int buscarDiasSancion(long dias) {
		
		int diasSancion = 0;
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select * from politica_sancion ps where ps.dias_desde<=? and ps.dias_hasta>=?");
			rs = pstmt.executeQuery();
			if(rs!=null && rs.next()) {
				diasSancion = rs.getInt("dias_sancion");
			}
			return diasSancion;
		} catch (SQLException e) {
			e.printStackTrace();
			return diasSancion;
		} finally { 
			try {
				if(pstmt!=null)pstmt.close();
				this.releaseConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
