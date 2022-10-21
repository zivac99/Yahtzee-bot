package com.comtrade.yamb.player.Luka;

import com.comtrade.yamb.Constants;
import com.comtrade.yamb.Die;
import com.comtrade.yamb.Request;
import com.comtrade.yamb.Response;
import com.comtrade.yamb.player.Luka.*;
import com.comtrade.yamb.FieldData;





public class KolonaBottomUp {

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
		if (ucestalostKockica[Patterns.vrednostMaxMetode(brojeviNaKockicama)] == 5) 
		{
			if (request.getBoard().getColumns()[2].getFields().get(14).isPlayable()) 
			{
				PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
						new FieldData(Konstante.KOLONA_BOTTOMUP, Konstante.YAMB));
				return response;
			}
			
		}
		//POKER
		else if(ucestalostKockica[Patterns.vrednostMaxMetode(brojeviNaKockicama)] == 4) 
		{
			if (request.getBoard().getColumns()[2].getFields().get(13).isPlayable()) 
			{
			PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
					new FieldData(Konstante.KOLONA_BOTTOMUP, Konstante.POKER));
			return response;
			}
		} 
		
		//FULL HOUSE
		else if (Patterns.ImamoLiFullHouse(brojeviNaKockicama) == true) 
		{
			if(Patterns.ImamoLiFullHouse(brojeviNaKockicama) == true)
			{
				if (request.getBoard().getColumns()[2].getFields().get(12).isPlayable()) 
				{
					PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
							new FieldData(Konstante.KOLONA_BOTTOMUP, Konstante.FULL));
					return response;
				}
			}
			else if (request.getBoard().getColumns()[2].getFields().get(12).isPlayable()) 
			{
				PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
						new FieldData(Konstante.KOLONA_BOTTOMUP, Konstante.FULL));
				return response;
			}
		}		
		
		//TRILING
		else if (ucestalostKockica[Patterns.vrednostMaxMetode(brojeviNaKockicama)] == 3) {
			if (request.getBoard().getColumns()[2].getFields().get(11).isPlayable()) 
			{
			PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
					new FieldData(Konstante.KOLONA_BOTTOMUP, Konstante.TRILING));
			return response;
			}
		}		
		
		//KENTA
		else if (Patterns.ImamoLiKenta(brojeviNaKockicama) == true) 
		{
			if (request.getBoard().getColumns()[2].getFields().get(10).isPlayable()) 
			{
			PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
					new FieldData(Konstante.KOLONA_BOTTOMUP, Konstante.KENTA));
			return response;
			}
		} 
		
		//MIN KOLONA
		else if(Patterns.poljeMin(kockice) == true)
		{
			if (request.getBoard().getColumns()[2].getFields().get(8).isPlayable()) 
			{
			PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
					new FieldData(Konstante.KOLONA_BOTTOMUP, Konstante.MIN));
			return response;
			}
		}
		
		//MAX KOLONA
		else if(Patterns.poljeMax(kockice) == true)
		{
			if (request.getBoard().getColumns()[2].getFields().get(7).isPlayable()) 
			{
			PlayerResponse response = new PlayerResponse(true, cuvanjeKockica,
					new FieldData(Konstante.KOLONA_BOTTOMUP, Konstante.MAX));
			return response;
			}
		}
		
		else if(ucestalostKockica[Patterns.vrednostMaxMetode(brojeviNaKockicama)]>=2)
		{
			for(int i=0; i<Constants.DICE_COUNT; i++)
			{	

			if (brojKojiSeNajvisePonavlja == i+1)// proveravamo da li je index broj 1
			{
				if (request.getBoard().getColumns()[2].getFields().get(i).isPlayable() == true) {
					PlayerResponse response = new PlayerResponse(false, cuvanjeKockica,
							new FieldData(Konstante.KOLONA_BOTTOMUP, i));
					return response;
				}
			}
			
			}
		}
		
		
		
		
		return null;
	}
	
}