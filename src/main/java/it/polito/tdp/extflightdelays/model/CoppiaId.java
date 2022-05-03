package it.polito.tdp.extflightdelays.model;

public class CoppiaId {

		int idPartenza; 
		int IdArrivo;
		double distanza; 
		
		
		public CoppiaId(int idPartenza, int idArrivo, double distanza) {
			this.idPartenza = idPartenza;
			this.IdArrivo = idArrivo;
			this.distanza=distanza; 
			
		}
		public int getIdPartenza() {
			return idPartenza;
		}
		public void setIdPartenza(int idPartenza) {
			this.idPartenza = idPartenza;
		}
		public int getIdArrivo() {
			return IdArrivo;
		}
		public void setIdArrivo(int idArrivo) {
			IdArrivo = idArrivo;
		}
		public double getDistanza() {
			return distanza;
		}
		public void setDistanza(int distanza) {
			this.distanza = distanza;
		} 
		
}
