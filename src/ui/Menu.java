package ui;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Scanner;

import data.DbHandler;
import entities.*;

public class Menu {
	
	private MenuABM mABM = new MenuABM();
	protected Scanner scan = null;
	protected DbHandler db = null;
	protected String dateFormat ="dd/MM/yyyy";
	
	public void start() {
		
		scan = new Scanner(System.in);
		int rta;
		db = new DbHandler();
			
		do {
			
			rta = menu();
			
			switch(rta) {
				case 1: 
					mABM.start();
					break;
				case 2:
					listarSocios();
					break;
				case 3:
					listarLibros();
					break;
				case 4:
					retirarLibro();
					break;
				case 5:
					devolverLibro();
					break;
				default:
					break;	
			}
			
		} while(rta!=0);
		
		
	}

	private void devolverLibro() {
		
		Ejemplar e = new Ejemplar();
		Libro l = new Libro();
		Socio s = new Socio();
		LocalDate fechaDevolucion = null;
		long dias = 0;
		int diasSancion = 0;
		
		
		System.out.print("Ingrese id de ejemplar: ");
		e.setIdEjemplar(Integer.parseInt(scan.nextLine()));
		
		l = db.buscarLibroXEjemplar(e);
		if(l!=null) {
			if (db.ejemplarPendiente(e)) {
				s = db.buscarSocioXEjemplar(e);
				System.out.println(s);
				System.out.println(l);
			} else System.out.println("Id ingresado no esta pendiente de devolucion");
		} else System.out.println("Id no corresponde a un ejemplar");	
		fechaDevolucion = db.calcularFechaDevolucion(e);
		dias = ChronoUnit.DAYS.between(LocalDate.now(), fechaDevolucion);
		if(dias>0) {
			db.registrarSancion(dias, s);
			diasSancion = db.buscarDiasSancion(dias);
			System.out.println("El socio tendra una sancion de " + diasSancion + " dias." );
		}
		if(db.registrarDevolucion(e)>0) {
			System.out.println("Devolucion registrada con exito");
		} else System.out.println("No se registro devolucion");
	}

	private void retirarLibro() {
		
		PoliticaPrestamo pp = new PoliticaPrestamo();
		Socio s = new Socio();
		LinkedList<Ejemplar> ejemplares = new LinkedList<Ejemplar>();
		LinkedList<Libro> librosRetirados = new LinkedList<Libro>();
		int prestamos = 0;
		String rta = new String();
		int idPrestamo = 0;
		
		System.out.print("Ingrese id de socio: ");
		s.setIdSocio(Integer.parseInt(scan.nextLine()));
		
		if(cumpleRequisitos(pp, s, prestamos)) {
			librosRetirados = db.librosRetiradosXSocio(s);
			do {
				agregarLibroAPrestamo(s, ejemplares, librosRetirados);
				prestamos++;
				System.out.print("Desea agregar otro ejemplar al prestamo? (s/n)");
				rta = scan.nextLine();
			} while(prestamos<=pp.getCantMaxLibrosPend() && rta.equals("s"));
			idPrestamo = registrarPrestamo(s, ejemplares);
			if(idPrestamo!=0) {
				for(Ejemplar e : ejemplares) {
					db.registrarLineaPrestamo(idPrestamo, e);
				}
				System.out.println("Prestamo registrado con exito");
			}
		}
	}

	private int registrarPrestamo(Socio s, LinkedList<Ejemplar> ejemplares) {
		
		int dias = 100, aux1 = 0;
		
		for(Ejemplar e : ejemplares) {
			aux1 = db.buscarMaxCantDias(e);
			if(dias>aux1) dias=aux1;
		}
		return db.registrarPrestamo(s, dias);
	}

	private void agregarLibroAPrestamo(Socio s, LinkedList<Ejemplar> ejemplares, LinkedList<Libro> librosRetirados) {
		
		Libro l = new Libro();
		Ejemplar e = new Ejemplar();
		
		System.out.print("Ingrese id de ejemplar a retirar: ");
		e.setIdEjemplar(Integer.parseInt(scan.nextLine()));
		
		l = db.buscarLibroXEjemplar(e);
		
		if(librosRetirados.contains(l)) {
			System.out.println("El socio ya retiro un ejemplar del mismo libro");
		} else {
			ejemplares.add(e);
			System.out.println(l);
		}
	}

	private boolean cumpleRequisitos(PoliticaPrestamo pp, Socio s, int prestamos) {
		
		if(db.estaSancionado(s)) {
			System.out.println("Socio sancionado");
			return false;
		} else {
				pp = db.buscarPoliticaP(pp);
				prestamos = db.cuantosPrestamos(s);
				if (prestamos<pp.getCantMaxLibrosPend()) {
					s = db.buscarSocio(s);
					System.out.println(s);
					return true;
				} else {
					System.out.println("Limite de prestamos alcanzado");
					return false;
				}
			}
	}

	private void listarLibros() {

		for(Libro l : db.listarLibros()) {
			System.out.println(l);
		}
		
	}

	private int menu() {
		
		System.out.println("Menu de opciones");
		System.out.println();
		System.out.println("1. ABM");
		System.out.println("2. Listado de Socios");
		System.out.println("3. Listado de Libros");
		System.out.println("4. Registrar Prestamo");
		System.out.println("5. Devolucion Prestamo");
		System.out.println("0. Exit");
		
		return Integer.parseInt(scan.nextLine());
	}
	
	private void listarSocios() {
		 
		for (Socio s : db.listarSocios()) {
			System.out.println(s);
		}
	}
	
	
	
}
