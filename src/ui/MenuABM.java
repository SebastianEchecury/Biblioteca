package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import entities.*;
import data.*;

public class MenuABM {
	
	protected Scanner scan = null;
	protected DbHandler db = null;
	protected String dateFormat ="dd/MM/yyyy";
	
	public void start() {
		
		db = new DbHandler();
		scan = new Scanner(System.in);
		int rta=0;
		
		do {
			
			switch(rta = menu()){
				case 1:
					altaSocio();
					break;
				case 2:
					bajaSocio();
					break;
				case 3:
					modSocio();
					break;
				case 4:
					altaLibro();
					break;
				case 5:
					bajaLibro();
					break;
				case 6:
					modLibro();
					break;
				case 7:
					modPoliticaPrestamo();
					break;
				case 8:
					modPoliticaSancion();
					break;
				case 9:
					altaEjemplar();
					break;
				case 10:
					eliminarEjemplar();
					break;
				default:
					break;
						
			}
			
		} while (rta!=0);
		
	}


private void eliminarEjemplar() {
		
		Libro l = new Libro();
		Ejemplar e = new Ejemplar();
		
		System.out.println("Ingrese id de ejemplar: ");
		e.setIdEjemplar(Integer.parseInt(scan.nextLine()));
		
		l = db.buscarLibroXEjemplar(e);
		if (l!=null) {
			System.out.println(l);
			System.out.print("Desea eliminarlo? (s/n)");
			String rta = scan.nextLine();
			if(rta.equals("s")) {
				db.eliminarEjemplar(e);
				System.out.println("Ejemplar eliminado con exito");
			}
		} else System.out.println("Ejempalr no encontrado.");
	}


	private void altaEjemplar() {
		
		Libro libro = new Libro();
		int cambios;
		
		for (Libro l : db.listarLibros()) {
			System.out.println(l);
		}
		System.out.print("Ingrese id de libro: ");
		libro.setIdLibro(Integer.parseInt(scan.nextLine()));
		System.out.print("Cantidad de ejemplares: ");
		int cant = Integer.parseInt(scan.nextLine());
		
		cambios = db.altaEjemplar(libro, cant);
		if (cambios>0)System.out.println("Ejemplares cargados con exito!");
		else System.out.println("Fallo al cargar ejemplares");
	}


	private void modPoliticaSancion() {
		
		int cambios = 0;
		PoliticaSancion p = new PoliticaSancion();
		
		for(PoliticaSancion ps : db.listarPoliticaS()) {
			System.out.println(ps);
		}
		
		System.out.print("Ingrese id de politica a modificar: ");
		p.setIdPolitica(Integer.parseInt(scan.nextLine()));
		System.out.print("Ingrese cantidad de dias de sancion: ");
		p.setDiasSancion(Integer.parseInt(scan.nextLine()));
		cambios = db.modPolitcaS(p);
		if(cambios == 1)System.out.println("Modificacion exitosa");
		else System.out.println("Fallo al modificar");
		
	}


	private void modPoliticaPrestamo() {
		
		PoliticaPrestamo pp = new PoliticaPrestamo();
		
		pp = db.buscarPoliticaP(pp);
		if(pp!=null) {
			System.out.println("Politica actual");
			System.out.println(pp);
			System.out.print("Desea modificarla? (s/n): ");
			String op = scan.nextLine();
			if (op.equals("s")) {
				System.out.print("Ingrese cantidad de libros: ");
				pp.setCantMaxLibrosPend(Integer.parseInt(scan.nextLine()));
				pp.setFecha(LocalDate.now());
				db.altaPoliticaP(pp);
			} 
		} else System.out.println("Politica no encontrada");	
	}


	private void modLibro() {
		
		Libro l = new Libro();
		
		System.out.print("Ingrese id de libro: ");
		l.setIdLibro(Integer.parseInt(scan.nextLine()));
		
		Libro libro = db.buscarLibro(l);
		if (libro!=null) {
			System.out.println(libro);
			this.cargaLibro(l);
			db.modLibro(l);
		} else System.out.println("Libro no encontrado");
		
	
	}


	private void bajaLibro() {

		Libro l = new Libro();
		
		System.out.print("Ingrese id de libro: ");
		l.setIdLibro(Integer.parseInt(scan.nextLine()));
		
		l = db.buscarLibro(l);
		if(l != null) {
			System.out.println(l);
			System.out.print("Confirmar baja (s/n)");
			String op = scan.nextLine();
			if (op.equals("s"))db.bajaLibro(l);
		} else {
			System.out.println("Libro no encontrado");
		}
	}


	private void altaLibro() {

		Libro l = new Libro();
		
		System.out.println("Ingreso de libro");
		cargaLibro(l);
		
		System.out.println("Id generado: "+db.altaLibro(l));
	}


	private void cargaLibro(Libro l) {
		System.out.print("Ingrese titulo: ");	
		l.setTitulo(scan.nextLine());
		
		System.out.print("Ingrese isbn: ");	
		l.setIsbn(scan.nextLine());
		
		System.out.print("Ingrese nro de edicion: ");
		l.setNroEdicion(scan.nextLine());
		
		DateTimeFormatter dFormat = DateTimeFormatter.ofPattern(dateFormat);
		System.out.println("Ingrese fecha de edicion ("+dateFormat+"): ");
		l.setFechaEdicion(LocalDate.parse(scan.nextLine(), dFormat));
		
		System.out.print("Ingrese cantidad maxima dias de prestamo: ");
		l.setMaxDiasPrestamo(Integer.parseInt(scan.nextLine()));
	}


	private void modSocio() {
		
		Socio s = new Socio();
		
		System.out.print("Ingrese id de socio a modificar: ");
		s.setIdSocio(Integer.parseInt(scan.next()));
		
		Socio socio = db.buscarSocio(s);
		if (socio!=null) {
			System.out.println("Datos actuales");
			System.out.println(socio);
			this.cargarSocio(s);
			db.modSocio(s);
		} else {
			System.out.println("Socio no encontrado");
		}
	}


	private void bajaSocio() {
		
		Socio s = new Socio();
		
		System.out.print("Ingrese id de socio a eliminar: ");
		s.setIdSocio(Integer.parseInt(scan.nextLine()));
		
		s = db.buscarSocio(s);
		if(s != null) {
			System.out.println(s);
			System.out.print("Confirmar baja (s/n)");
			String op = scan.nextLine();
			if (op.equals("s"))db.bajaSocio(s);
		} else {
			System.out.println("Socio no encontrado");
		}
		
	}


	private void altaSocio() {
		
		Socio s = new Socio();
	
		System.out.println("Ingreso de socio");
		System.out.println();
		
		cargarSocio(s);
	
		System.out.println("Id generado: "+db.altaSocio(s));
		
	}

	private void cargarSocio(Socio s) {
		System.out.print("Ingrese apellido: ");
		s.setApellido(scan.nextLine());
		System.out.print("Ingrese nombre: ");
		s.setNombre(scan.nextLine());
		System.out.print("Ingrese mail: ");
		s.setMail(scan.nextLine());
		System.out.print("Ingrese domicilio: ");
		s.setDomicilio(scan.nextLine());
		System.out.print("Ingrese telefono: ");
		s.setTelefono(scan.nextLine());
	}

	private int menu() {

		System.out.println("Menu ABM");
		System.out.println();
		System.out.println("1. Alta socio");
		System.out.println("2. Baja socio");
		System.out.println("3. Modificar socio");
		System.out.println("4. Alta libro");
		System.out.println("5. Baja libro");
		System.out.println("6. Modificar libro");
		System.out.println("7. Modificar politica de prestamo");
		System.out.println("8. Modificar politica de sancion");
		System.out.println("9. Cargar ejemplares");
		System.out.println("10. Eliminar ejemplar");
		System.out.println("0. Atras");
		
		return Integer.parseInt(scan.nextLine());
	}

}
