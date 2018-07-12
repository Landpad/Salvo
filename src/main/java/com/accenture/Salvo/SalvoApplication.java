package com.accenture.Salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}



	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
		return (args) -> {
			// save a couple of customers

			Player Player1 = new Player("j.bauer@ctu.gov","24");
			Player Player2 = new Player("c.obrian@ctu.gov","42");
			Player Player3 = new Player("kim_bauer@gmail.com","kb");
			Player Player4 = new Player("t.almeida@ctu.gov","mole");
			playerRepository.save(Player1);
			playerRepository.save(Player2);
			playerRepository.save(Player3);
			playerRepository.save(Player4);



			Date date= new Date();
			Date date1=  Date.from(date.toInstant().plusSeconds(3600));
			Date date2=  Date.from(date.toInstant().plusSeconds(7200));
			Game Game1 = new Game(date);
			Game Game2 = new Game(date1);
			Game Game3 = new Game(date2);
			Game Game4 = new Game(date);
			Game Game5 = new Game(date);
			Game Game6 = new Game(date);
			Game Game7 = new Game(date);
			Game Game8 = new Game(date);


			gameRepository.save(Game1);
			gameRepository.save(Game2);
			gameRepository.save(Game3);
			gameRepository.save(Game4);
			gameRepository.save(Game5);
			gameRepository.save(Game6);
			gameRepository.save(Game7);
			gameRepository.save(Game8);

			Date joinDate = new Date();
			GamePlayer gamePlayer1 = new GamePlayer(Game1, Player1, joinDate);
			GamePlayer gamePlayer2 = new GamePlayer(Game1, Player2, joinDate);

			GamePlayer gamePlayer3 = new GamePlayer(Game2, Player1, joinDate);
			GamePlayer gamePlayer4 = new GamePlayer(Game2, Player2, joinDate);

			GamePlayer gamePlayer5 = new GamePlayer(Game3, Player2, joinDate);
			GamePlayer gamePlayer6 = new GamePlayer(Game3, Player4, joinDate);

			GamePlayer gamePlayer7 = new GamePlayer(Game4, Player2, joinDate);
			GamePlayer gamePlayer8 = new GamePlayer(Game4, Player1, joinDate);

			GamePlayer gamePlayer9 = new GamePlayer(Game5, Player4, joinDate);
			GamePlayer gamePlayer10 = new GamePlayer(Game5, Player1, joinDate);

			GamePlayer gamePlayer11= new GamePlayer(Game6, Player3, joinDate);


			GamePlayer gamePlayer12 = new GamePlayer(Game7, Player4, joinDate);

			GamePlayer gamePlayer13 = new GamePlayer(Game8, Player3, joinDate);
			GamePlayer gamePlayer14 = new GamePlayer(Game8, Player4, joinDate);


			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);
			gamePlayerRepository.save(gamePlayer7);
			gamePlayerRepository.save(gamePlayer8);
			gamePlayerRepository.save(gamePlayer9);
			gamePlayerRepository.save(gamePlayer10);
			gamePlayerRepository.save(gamePlayer11);
			gamePlayerRepository.save(gamePlayer12);
			gamePlayerRepository.save(gamePlayer13);
			gamePlayerRepository.save(gamePlayer14);


			List<String> lista1 = Arrays.asList("H1","H2","H3","H4");
			List<String> lista2 = Arrays.asList("F3","G3","H3");
			List<String> lista3 = Arrays.asList("A1","B2","C3");
			List<String> lista4 = Arrays.asList("F3","F4","F5");
			List<String> lista5 = Arrays.asList("J1","J2","J3","J4");

			Ship Ship1 = new Ship("Destroyer",gamePlayer1,lista5);
			Ship Ship2 = new Ship("Submarine",gamePlayer1,lista1);
			Ship Ship3 = new Ship("Patrol Boat",gamePlayer1,lista5);
			Ship Ship4 = new Ship("Destroyer",gamePlayer1,lista5);
			Ship Ship5 = new Ship("Patrol Boat",gamePlayer1,lista1);

			Ship Ship6 = new Ship("Destroyer",gamePlayer2,lista5);
			Ship Ship7 = new Ship("Patrol Boat",gamePlayer2,lista5);
			Ship Ship8 = new Ship("Submarine",gamePlayer2,lista1);
			Ship Ship9 = new Ship("Patrol Boat",gamePlayer2,lista5);

			Ship Ship10 = new Ship("Destroyer",gamePlayer3,lista5);
			Ship Ship11 = new Ship("Patrol Boat",gamePlayer3,lista1);
			Ship Ship12 = new Ship("Submarine",gamePlayer3,lista5);
			Ship Ship13 = new Ship("Patrol Boat",gamePlayer3,lista5);

			Ship Ship14 = new Ship("Submarine",gamePlayer1,lista1);
			Ship Ship15 = new Ship("Patrol Boat",gamePlayer1,lista5);



			shipRepository.save(Ship1);
			shipRepository.save(Ship2);
			shipRepository.save(Ship3);
			shipRepository.save(Ship4);
			shipRepository.save(Ship5);
			shipRepository.save(Ship6);
			shipRepository.save(Ship7);
			shipRepository.save(Ship8);
			shipRepository.save(Ship9);
			shipRepository.save(Ship10);
			shipRepository.save(Ship11);
			shipRepository.save(Ship12);
			shipRepository.save(Ship13);
			shipRepository.save(Ship14);
			shipRepository.save(Ship15);

            List<String> location1 = Arrays.asList("H1","B2","H3","H4");
            List<String> location2 = Arrays.asList("F1","B2","F3","F4");
            List<String> location3 = Arrays.asList("A1","A2","A3","A4");
            List<String> location4 = Arrays.asList("B1","B2","B3","B4");
            List<String> location5 = Arrays.asList("J1","J2","J3","J4");

			Salvo Salvo1 = new Salvo(gamePlayer1,1,location2);
            Salvo Salvo2 = new Salvo(gamePlayer2,1,location1);
            Salvo Salvo3 = new Salvo(gamePlayer1,2,location3);
            Salvo Salvo4 = new Salvo(gamePlayer2,2,location4);
			Salvo Salvo5 = new Salvo(gamePlayer3,1,location3);
			Salvo Salvo6 = new Salvo(gamePlayer4,1,location2);
			Salvo Salvo7 = new Salvo(gamePlayer3,2,location4);
			Salvo Salvo8 = new Salvo(gamePlayer4,2,location5);

			salvoRepository.save(Salvo1);
            salvoRepository.save(Salvo2);
            salvoRepository.save(Salvo3);
            salvoRepository.save(Salvo4);
			salvoRepository.save(Salvo5);
			salvoRepository.save(Salvo6);
			salvoRepository.save(Salvo7);
			salvoRepository.save(Salvo8);

			Score Score1 = new Score(1,Player1,Game1);
			Score Score2 = new Score(0,Player2,Game1);
			Score Score3 = new Score(0,Player2,Game2);
			Score Score4 = new Score(1,Player1,Game2);
			Score Score5 = new Score(0,Player3,Game3);
			Score Score6 = new Score(0,Player1,Game3);
			Score Score7 = new Score(0.5,Player4,Game4);
			Score Score8 = new Score(0.5,Player2,Game4);
			Score Score9 = new Score(0.5,Player3,Game5);
			Score Score10 = new Score(0.5,Player2,Game5);
			Score Score11 = new Score(1,Player1,Game6);
			Score Score12 = new Score(0,Player4,Game6);
			Score Score13 = new Score(0,Player4,Game7);
			Score Score14 = new Score(1,Player3,Game7);
			Score Score15 = new Score(0,Player3,Game8);
			Score Score16 = new Score(0,Player4,Game8);

			scoreRepository.save(Score1);
			scoreRepository.save(Score2);
			scoreRepository.save(Score3);
			scoreRepository.save(Score4);
			scoreRepository.save(Score5);
			scoreRepository.save(Score6);
			scoreRepository.save(Score7);
			scoreRepository.save(Score8);
			scoreRepository.save(Score9);
			scoreRepository.save(Score10);
			scoreRepository.save(Score11);
			scoreRepository.save(Score12);
			scoreRepository.save(Score13);
			scoreRepository.save(Score14);
			scoreRepository.save(Score15);
			scoreRepository.save(Score16);



		};



	}}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository repoPlayer;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = repoPlayer.findByUserName(inputName);
			if (player != null) {
				return new User(player.getusername(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/api/players").permitAll()
				.antMatchers("/api/game_view/**").hasAuthority("USER")
				.antMatchers("/web/game.html").hasAuthority("USER")
				.antMatchers("/api/**").permitAll()
				.antMatchers("/web/**").permitAll()

				.anyRequest().fullyAuthenticated()

				.anyRequest().permitAll();

	http.formLogin()
		.usernameParameter("username")
    	.passwordParameter("password")
    	.loginPage("/api/login");

 	http.logout().logoutUrl("/api/logout");

 	// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
	}




