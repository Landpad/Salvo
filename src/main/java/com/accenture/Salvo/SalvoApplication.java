package com.accenture.Salvo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Date;


@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository) {
		return (args) -> {
			// save a couple of customers

			Player Player1 = new Player("j.bauer@ctu.gov","Jack","Bauer");
			Player Player2 = new Player("c.obrian@ctu.gov","Chloe","O'Brian");
			Player Player3 = new Player("kim_bauer@gmail.com","Kim","Bauer");
			Player Player4 = new Player("t.almeida@ctu.gov","Tony","Almeida");
			playerRepository.save(Player1);
			playerRepository.save(Player2);
			playerRepository.save(Player3);
			playerRepository.save(Player4);

			playerRepository.save(new Player("jbalvin@hotmail.com","",""));
			playerRepository.save(new Player("tuco","",""));
			playerRepository.save(new Player("alo","",""));
			playerRepository.save(new Player("jejox","",""));


			Date date= new Date();
			Date date1=  Date.from(date.toInstant().plusSeconds(3600));
			Date date2=  Date.from(date.toInstant().plusSeconds(7200));
			Game Game1 = new Game(date);
			Game Game2 = new Game(date1);
			Game Game3 = new Game(date2);


			gameRepository.save(Game1);
			gameRepository.save(Game2);
			gameRepository.save(Game3);

			Date joinDate = new Date();
			GamePlayer gamePlayer1 = new GamePlayer(Game1, Player1,joinDate);
			GamePlayer gamePlayer2 = new GamePlayer(Game2, Player2,joinDate);
			GamePlayer gamePlayer3 = new GamePlayer(Game3, Player3,joinDate);

			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);






		};





	};}