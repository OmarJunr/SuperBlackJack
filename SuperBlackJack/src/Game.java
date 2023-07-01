import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

public class Game {

   // CLASSES
   private static JFrame frame  = new MainFrame();
   private static AudioAsset aa = new AudioAsset();
   private static SE se         = new SE();
   private static Music music   = new Music();
   private static CardGroup deck, dealerCards, playerCards; //Declarando Variáveis:
   private static CardGroupPanel dealerCardPainel = null, playerCardPainel = null;
   private static Cards dealerCartaOculta;
   private static Timer countdownTimer;

   private static double saldo = 0.0;
   private static int qtdApostada = 0, roundCount = 0, timeRemaining = 60;

   // Criando os elementos da interface no WindowBuilder
   private static JTextField tfSaldo;
   private static JTextField tfVlrAposta;

   // Botões da Tela
   private static JButton btnNovoJogo;
   private static JButton btnSair;
   private static JButton btnApostar;
   private static JButton btnPegar;
   private static JButton btnManter;
   private static JButton btnDobrar;
   private static JButton btnContinuar;

   //Labels da tela 
   private static JLabel lblSaldoInicial;
   private static JLabel lblInserirAposta;
   private static JLabel lblSaldoAtual;
   private static JLabel lblVlrSaldo;
   private static JLabel lblDealer;
   private static JLabel lblJogador;
   private static JLabel lblVlrAposta;
   private static JLabel lblVlrApostaDesc;
   private static JLabel lblInfo;
   private static JLabel lblTimer;
   private static JLabel lblShuffleInfo = null;
   
   public static boolean isValidAmount(String s) {
      try {
         if (Integer.parseInt(s) > 0)
            return true;
         else
            return false;
      } catch (NumberFormatException e) {
         return false;
      }
   }

   public static void initObjects() {
      btnNovoJogo = new JButton("Novo Jogo");
      btnNovoJogo.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           playMusic(aa.bgm);

            startGame();
         }
      });
      btnNovoJogo.setBounds(20, 610, 99, 50);
      frame.getContentPane().add(btnNovoJogo);

      btnSair = new JButton("Sair");
      btnSair.setEnabled(false);
      btnSair.setBounds(121, 610, 99, 50);
      btnSair.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            frame.getContentPane().removeAll();
            stopMusic(aa.bgm);
            frame.repaint();
            initObjects();
         }
      });
      frame.getContentPane().add(btnSair);

      tfSaldo = new JTextField();
      tfSaldo.setText("100");
      tfSaldo.setBounds(131, 580, 89, 28);
      frame.getContentPane().add(tfSaldo);
      tfSaldo.setColumns(10);

      lblSaldoInicial = new JLabel("Saldo inicial:");
      lblSaldoInicial.setFont(new Font("Arial", Font.BOLD, 13));
      lblSaldoInicial.setForeground(Color.WHITE);
      lblSaldoInicial.setBounds(30, 586, 100, 16);
      frame.getContentPane().add(lblSaldoInicial);
   }

   public static void showBet() {

      btnSair.setEnabled(true);

      lblSaldoAtual = new JLabel("Saldo atual:");
      lblSaldoAtual.setHorizontalAlignment(SwingConstants.CENTER);
      lblSaldoAtual.setFont(new Font("Arial", Font.BOLD, 16));
      lblSaldoAtual.setForeground(Color.WHITE);
      lblSaldoAtual.setBounds(315, 578, 272, 22);
      frame.getContentPane().add(lblSaldoAtual);

      lblVlrSaldo = new JLabel();
      lblVlrSaldo.setText(String.format("$%.2f", saldo));
      lblVlrSaldo.setForeground(Color.ORANGE);
      lblVlrSaldo.setFont(new Font("Arial", Font.BOLD, 40));
      lblVlrSaldo.setHorizontalAlignment(SwingConstants.CENTER);
      lblVlrSaldo.setBounds(315, 600, 272, 50);
      frame.getContentPane().add(lblVlrSaldo);

      lblInfo = new JLabel("Insira um valor e clique em Apostar");
      lblInfo.setBackground(Color.ORANGE);
      lblInfo.setOpaque(false);
      lblInfo.setForeground(Color.ORANGE);
      lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
      lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
      lblInfo.setBounds(290, 482, 320, 28);
      frame.getContentPane().add(lblInfo);

      tfVlrAposta = new JTextField();
      tfVlrAposta.setText("10");
      tfVlrAposta.setBounds(790, 580, 89, 28);
      frame.getContentPane().add(tfVlrAposta);

      lblInserirAposta = new JLabel("Insira a aposta:");
      lblInserirAposta.setFont(new Font("Arial", Font.BOLD, 14));
      lblInserirAposta.setForeground(Color.WHITE);
      lblInserirAposta.setBounds(680, 586, 150, 16);
      frame.getContentPane().add(lblInserirAposta);

      btnApostar = new JButton("Apostar");
      btnApostar.setBounds(679, 610, 200, 50);
      btnApostar.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            apostar();
         }
      });
      frame.getContentPane().add(btnApostar);
      btnApostar.requestFocus();

      frame.repaint();

   }

   public static void apostar() {

      if (lblShuffleInfo != null)
         frame.getContentPane().remove(lblShuffleInfo);

      dealerCards = new CardGroup();
      playerCards = new CardGroup();

      if (isValidAmount(tfVlrAposta.getText()) == true) {
         qtdApostada = Integer.parseInt(tfVlrAposta.getText());
      } else {
         lblInfo.setText("Erro: Insira um número natural!");
         tfVlrAposta.requestFocus();
         return;
      }

      if (qtdApostada > saldo) {
         lblInfo.setText("Erro: Apostou mais do que o saldo!");
         tfVlrAposta.requestFocus();
         return;
      }
      saldo -= qtdApostada;

      lblVlrSaldo.setText(String.format("$%.2f", saldo));

      tfVlrAposta.setEnabled(false);
      btnApostar.setEnabled(false);

      if (countdownTimer != null) {
          countdownTimer.cancel();
       }
      startCountdown();

      lblInfo.setText("Deseja: Pegar, Manter ou Dobrar?");

      lblDealer = new JLabel("Dealer");
      lblDealer.setForeground(Color.WHITE);
      lblDealer.setFont(new Font("Arial Black", Font.BOLD, 20));
      lblDealer.setBounds(415, 76, 82, 28);
      frame.getContentPane().add(lblDealer);

      lblJogador = new JLabel("Você");
      lblJogador.setForeground(Color.WHITE);
      lblJogador.setFont(new Font("Arial Black", Font.BOLD, 20));
      lblJogador.setBounds(415, 266, 100, 28);
      frame.getContentPane().add(lblJogador);

      btnPegar = new JButton("Pegar");
      btnPegar.setBounds(290, 515, 100, 35);
      btnPegar.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            pegar();
         }
      });
      frame.getContentPane().add(btnPegar);
      btnPegar.requestFocus();

      btnManter = new JButton("Manter");
      btnManter.setBounds(400, 515, 100, 35);
      btnManter.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            manter();
         }
      });
      frame.getContentPane().add(btnManter);

      btnDobrar = new JButton("Dobrar");
      btnDobrar.setBounds(510, 515, 100, 35);
      btnDobrar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              dobrar();
          }
      });
      frame.getContentPane().add(btnDobrar);

      btnContinuar = new JButton("Continuar");
      btnContinuar.setEnabled(false);
      btnContinuar.setVisible(false);
      btnContinuar.setBounds(290, 444, 320, 35);
      btnContinuar.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            aceitarResultado();
         }
      });
      frame.getContentPane().add(btnContinuar);

      lblTimer = new JLabel("1:00");
      lblTimer.setFont(new Font("Arial", Font.BOLD, 20));
      lblTimer.setForeground(Color.WHITE);
      lblTimer.setBounds(10, 10, 60, 16);
      frame.getContentPane().add(lblTimer);

      lblVlrAposta = new JLabel();
      lblVlrAposta.setText("$" + qtdApostada);
      lblVlrAposta.setHorizontalAlignment(SwingConstants.CENTER);
      lblVlrAposta.setForeground(Color.ORANGE);
      lblVlrAposta.setFont(new Font("Arial", Font.BOLD, 40));
      lblVlrAposta.setBounds(679, 488, 200, 50);
      frame.getContentPane().add(lblVlrAposta);

      lblVlrApostaDesc = new JLabel("Valor da Aposta:");
      lblVlrApostaDesc.setHorizontalAlignment(SwingConstants.CENTER);
      lblVlrApostaDesc.setForeground(Color.WHITE);
      lblVlrApostaDesc.setFont(new Font("Arial", Font.BOLD, 16));
      lblVlrApostaDesc.setBounds(689, 465, 190, 22);
      frame.getContentPane().add(lblVlrApostaDesc);

      frame.repaint();

      dealerCartaOculta = deck.takeCard();
      playSE(aa.cardSound01);
      dealerCards.cards.add(new Cards("", "", 0));
      dealerCards.cards.add(deck.takeCard());
      playSE(aa.cardSound01);

      playerCards.cards.add(deck.takeCard());
      playSE(aa.cardSound01);
      playerCards.cards.add(deck.takeCard());
      playSE(aa.cardSound01);

      updateCardPanels();
      verificaSimples();

   }

   public static void pegar() {

      playerCards.cards.add(deck.takeCard());
      playSE(aa.cardSound01);
      updateCardPanels();

      verificaSimples();

   }

   public static boolean verificaSimples() {
      boolean outcomeHasHappened = false;
      int playerScore = playerCards.getTotalValue();
      if (playerScore > 21 && playerCards.getNumAces() > 0)
         playerScore -= 10;

      if (playerScore == 21) {

         dealerCards.cards.set(0, dealerCartaOculta);
         updateCardPanels();
         if (dealerCards.getTotalValue() == 21) {
            playSE(aa.draw);
            lblInfo.setText("Empate!");
            saldo += qtdApostada;
         } else {
            playSE(aa.youwon);
            lblInfo.setText(String.format("Você tem o Blackjack! Lucro: $%.2f", 1.5f * qtdApostada));
            saldo += 2.5f * qtdApostada;
         }
         lblVlrSaldo.setText(String.format("$%.2f", saldo));

         outcomeHasHappened = true;
         resultadoAconteceu();

      } else if (playerScore > 21) {
         playSE(aa.youlost);
         lblInfo.setText("Você Perdeu! Perda: $" + qtdApostada);
         dealerCards.cards.set(0, dealerCartaOculta);
         updateCardPanels();
         outcomeHasHappened = true;
         resultadoAconteceu();
      }
      countdownTimer.cancel();
      resetCountdown();
      startCountdown();
      return outcomeHasHappened;

   }

   public static void dobrar() {
      if (saldo < qtdApostada) {
         lblInfo.setText("Erro: Saldo insuficiente para dobrar!");
         return;
      }
      saldo -= qtdApostada;
      qtdApostada *= 2;

      lblVlrAposta.setText("$" + qtdApostada);
      lblVlrSaldo.setText(String.format("$%.2f", saldo));

      pegar();

      if (!verificaSimples()) {
         manter();
      }
   }

   public static void manter() {
      if (verificaSimples())
         return;

      int playerScore = playerCards.getTotalValue();
      if (playerScore > 21 && playerCards.getNumAces() > 0)
         playerScore -= 10;

      dealerCards.cards.set(0, dealerCartaOculta);

      int dealerScore = dealerCards.getTotalValue();

      while (dealerScore < 16) {
         dealerCards.cards.add(deck.takeCard());
         playSE(aa.cardSound01);
         dealerScore = dealerCards.getTotalValue();
         if (dealerScore > 21 && dealerCards.getNumAces() > 0)
            dealerScore -= 10;
      }

      updateCardPanels();

      if (playerScore > dealerScore) {
         playSE(aa.youwon);
         lblInfo.setText("Você Ganhou! Lucro: $" + qtdApostada);
         saldo += qtdApostada * 2;
         lblVlrSaldo.setText(String.format("$%.2f", saldo));

      } else if (dealerScore == 21) {
         playSE(aa.youlost);
         lblInfo.setText("Dealer tem o Blackjack! Perda: $" + qtdApostada);

      } else if (dealerScore > 21) {
         playSE(aa.youwon);
         lblInfo.setText("Dealer Perdeu! Lucro: $" + qtdApostada);
         saldo += qtdApostada * 2;
         lblVlrSaldo.setText(String.format("$%.2f", saldo));
      } else if (playerScore == dealerScore) {
         playSE(aa.draw);
         lblInfo.setText("Empate!");
         saldo += qtdApostada;
         lblVlrSaldo.setText(String.format("$%.2f", saldo));
      } else {
         playSE(aa.youlost);
         lblInfo.setText("Dealer Ganhou! Perda: $" + qtdApostada);
      }

      resultadoAconteceu();

   }

   public static void resultadoAconteceu() {

      btnPegar.setEnabled(false);
      btnManter.setEnabled(false);
      btnDobrar.setEnabled(false);
      lblTimer.setVisible(false);
      countdownTimer.cancel();

      lblInfo.setOpaque(true);
      lblInfo.setForeground(Color.RED);
      new Timer().schedule(new TimerTask() {
         @Override
         public void run() {
            btnContinuar.setEnabled(true);
            btnContinuar.setVisible(true);
            btnContinuar.requestFocus();
         }
      }, 500);
   }

   public static void aceitarResultado() {
      if (countdownTimer != null) {
         countdownTimer.cancel();
      }
      lblInfo.setOpaque(false);
      lblInfo.setForeground(Color.ORANGE);

      frame.getContentPane().remove(lblDealer);
      frame.getContentPane().remove(lblJogador);
      frame.getContentPane().remove(btnPegar);
      frame.getContentPane().remove(btnManter);
      frame.getContentPane().remove(btnDobrar);
      frame.getContentPane().remove(lblVlrAposta);
      frame.getContentPane().remove(lblVlrApostaDesc);
      frame.getContentPane().remove(btnContinuar);
      frame.getContentPane().remove(dealerCardPainel);
      frame.getContentPane().remove(playerCardPainel);
      lblInfo.setText("Insira um valor e clique em Aposta");
      tfVlrAposta.setEnabled(true);
      btnApostar.setEnabled(true);
      btnApostar.requestFocus();
      frame.repaint();

      if (saldo <= 0) {
         int choice = JOptionPane.showOptionDialog(null, "Você ficou sem fundos. Pressione [Yes] para adicionar $ 100 ou [No] para encerrar o jogo atual.", "Sem fundos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

         if (choice == JOptionPane.YES_OPTION) {
            saldo += 100;
            lblVlrSaldo.setText(String.format("$%.2f", saldo));
         } else {
            frame.getContentPane().removeAll();
            frame.repaint();
            initObjects();
            return;
         }
      }

      roundCount++;
      if (roundCount >= 5) {
         deck.initFullDeck();
         deck.shuffle();

         lblShuffleInfo = new JLabel("O deck foi reabastecido e embaralhado!");
         lblShuffleInfo.setForeground(Color.ORANGE);
         lblShuffleInfo.setFont(new Font("Arial", Font.BOLD, 20));
         lblShuffleInfo.setHorizontalAlignment(SwingConstants.CENTER);
         lblShuffleInfo.setBounds(235, 307, 430, 42);
         frame.getContentPane().add(lblShuffleInfo);

         roundCount = 0;
      }
   }

   public static void startCountdown() {
      countdownTimer = new Timer();
      countdownTimer.scheduleAtFixedRate(new TimerTask() {
         @Override
         public void run() {
            timeRemaining--;

            if (timeRemaining <= 0) {
               countdownTimer.cancel();
               manter();
            }

            int minutes = timeRemaining / 60;
            int seconds = timeRemaining % 60;

            String timerText = String.format("%d:%02d", minutes, seconds);
            lblTimer.setText(timerText);
         }
      }, 0, 1000);
   }

   public static void resetCountdown() {
       timeRemaining = 60;
   }

   public static void startGame() {

      if (isValidAmount(tfSaldo.getText()) == true) {
         saldo = Integer.parseInt(tfSaldo.getText());
      } else {
         JOptionPane.showMessageDialog(frame, "Saldo inválido! Certifique-se de que é um número natural.", "Erro", JOptionPane.ERROR_MESSAGE);
         tfSaldo.requestFocus();
         return;
      }

      btnNovoJogo.setEnabled(false);
      tfSaldo.setEnabled(false);

      showBet();

      roundCount = 0;

      deck = new CardGroup();
      deck.initFullDeck();
      deck.shuffle();

   }

   public static void updateCardPanels() {
      if (dealerCardPainel != null) {
         frame.getContentPane().remove(dealerCardPainel);
         frame.getContentPane().remove(playerCardPainel);
      }
      // Criar e exibir dois painéis
      dealerCardPainel = new CardGroupPanel(dealerCards, 420 - (dealerCards.getCount() * 40), 110, 70, 104, 10);
      frame.getContentPane().add(dealerCardPainel);
      playerCardPainel = new CardGroupPanel(playerCards, 420 - (playerCards.getCount() * 40), 300, 70, 104, 10);
      frame.getContentPane().add(playerCardPainel);
      frame.repaint();
   }

   public static void playSE(URL url) {
      se.setFile(url);
      se.play(url);
   }

   public static void playMusic(URL url) {
      music.setFile(url);
      music.play(url);
      music.loop(url);
   }

   public static void stopMusic(URL url) {
      music.stop(url);
   }

   public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
      initObjects();

      frame.setVisible(true);
   }

   public static int heightFromWidth(int width) {
      return (int) (1f * width * (380f / 255f));
   }
}