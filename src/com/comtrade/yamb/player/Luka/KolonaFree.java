package com.comtrade.yamb.player.Luka;

import com.comtrade.yamb.Constants;
import com.comtrade.yamb.Die;
import com.comtrade.yamb.FieldData;
import com.comtrade.yamb.Request;

public class KolonaFree {

	public static PlayerResponse play(Request request) {

		Die[] kockice = request.getDice();
		int[] brojeviNaKockicama = Patterns.dieToInt(kockice);
		boolean[] cuvanjeKockica = new boolean[Constants.DICE_COUNT];

		int[] ucestalostKockica = Patterns.ucestalostBrojeva(brojeviNaKockicama);

		int brojKojiSeNajvisePonavlja = Patterns.brojKojiSeNajvisePonavlja(brojeviNaKockicama);

		for (int i = 0; i < Constants.DICE_COUNT; i++) {

			if (kockice[i].getValue() == brojKojiSeNajvisePonavlja) {
				cuvanjeKockica[i] = true;
			} else {
				cuvanjeKockica[i] = false;
			}

		}
		
		//YAMB
		if (ucestalostKockica[Patterns.vrednostMaxMetode(brojeviNaKockicama)]>=4) 
		{
			if (request.getBoard().getColumns()[1].getFields().get(14).isPlayable()) 
			{
				PlayerResponse response = new PlayerResponse(false, cuvanjeKockica,
						new FieldData(Konstante.KOLONA_FREE, Konstante.YAMB));
				return response;
			}
			
		}
		//POKER
		else if(ucestalostKockica[Patterns.vrednostMaxMetode(brojeviNaKockicama)]>=3) 
		{
			if (request.getBoard().getColumns()[1].getFields().get(13).isPlayable()) 
			{
				PlayerResponse response = new PlayerResponse(false, cuvanjeKockica,
						new FieldData(Konstante.KOLONA_FREE, Konstante.POKER));
				return response;
			}
		} 
		
		//KENTA
		else if (Patterns.ImamoLiKenta(brojeviNaKockicama) == true) 
		{
			if (request.getBoard().getColumns()[1].getFields().get(10).isPlayable()) 
			{
				PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
						new FieldData(Konstante.KOLONA_FREE, Konstante.KENTA));
				return response;
			}
			
		} 
		
		//FULL HOUSE
		else if (Patterns.najavaImamoLiFullHouse(brojeviNaKockicama) == true) 
		{	
			if(Patterns.ImamoLiFullHouse(brojeviNaKockicama) == true)
			{
				if (request.getBoard().getColumns()[1].getFields().get(12).isPlayable()) 
				{
					PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
							new FieldData(Konstante.KOLONA_FREE, Konstante.FULL));
					return response;
				}
			}
			else if (request.getBoard().getColumns()[1].getFields().get(12).isPlayable()) 
			{
				PlayerResponse response = new PlayerResponse(false, cuvanjeKockica,
						new FieldData(Konstante.KOLONA_FREE, Konstante.FULL));
				return response;
			}
				
		}
		
		
		//Popunjavanje kolona 1 do 6
		else if (ucestalostKockica[Patterns.vrednostMaxMetode(brojeviNaKockicama)]>=3) 
		{
				for(int i=0; i<Konstante.ROW_NUMBER; i++)
				{	

				if (brojKojiSeNajvisePonavlja == i+1)// proveravamo da li je index broj 1
				{
					if (request.getBoard().getColumns()[1].getFields().get(i).isPlayable()) {
						PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
								new FieldData(Konstante.KOLONA_RUCNA, i));
						return response;
					}
				}
				
				}

		}
		
		//TRILING
		else if (ucestalostKockica[Patterns.vrednostMaxMetode(brojeviNaKockicama)]>2) {
			if (request.getBoard().getColumns()[1].getFields().get(11).isPlayable()) 
			{
				PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
						new FieldData(Konstante.KOLONA_FREE, Konstante.TRILING));
				return response;
			}
			
		}
		//MAX KOLONA
		else if(Patterns.poljeMax(kockice) == true)
		{
			if (request.getBoard().getColumns()[1].getFields().get(7).isPlayable())
			{
				PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
						new FieldData(Konstante.KOLONA_FREE, Konstante.MAX));
				return response;
			}
			
		}
		//MIN KOLONA
		else if(Patterns.poljeMin(kockice) == true)
		{
			if (request.getBoard().getColumns()[1].getFields().get(8).isPlayable())
			{
				PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
						new FieldData(Konstante.KOLONA_FREE, Konstante.MIN));
				return response;
			}
			
		}
		
		return null;
}
}
