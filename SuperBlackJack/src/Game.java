import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
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
   private static Cards dealerHiddenCard;

   private static double saldo = 0.0;
   private static int betAmount = 0, roundCount = 0;

   // Criando os elementos da interface no WindowBuilder
   private static JTextField tfSaldo;
   private static JTextField tfVlrAposta;

   // Botões da Tela
   private static JButton btnNovoJogo;
   private static JButton btnSair;
   private static JButton btnDeal;
   private static JButton btnComprar;
   private static JButton btnManter;
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

      btnDeal = new JButton("Apostar");
      btnDeal.setBounds(679, 610, 200, 50);
      btnDeal.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            deal();
         }
      });
      frame.getContentPane().add(btnDeal);
      btnDeal.requestFocus();

      frame.repaint();

   }

   public static void deal() {

      if (lblShuffleInfo != null)
         frame.getContentPane().remove(lblShuffleInfo);

      dealerCards = new CardGroup();
      playerCards = new CardGroup();

      if (isValidAmount(tfVlrAposta.getText()) == true) {
         betAmount = Integer.parseInt(tfVlrAposta.getText());
      } else {
         lblInfo.setText("Erro: A aposta deve ser um número natural!");
         tfVlrAposta.requestFocus();
         return;
      }

      if (betAmount > saldo) {
         lblInfo.setText("Erro: Apostou mais do que o saldo!");
         tfVlrAposta.requestFocus();
         return;
      }
      saldo -= betAmount;

      lblVlrSaldo.setText(String.format("$%.2f", saldo));

      tfVlrAposta.setEnabled(false);
      btnDeal.setEnabled(false);

      lblInfo.setText("Deseja: Comprar ou Manter?");

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

      btnComprar = new JButton("Comprar");
      btnComprar.setBounds(290, 515, 140, 35);
      btnComprar.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            hit();
         }
      });
      frame.getContentPane().add(btnComprar);
      btnComprar.requestFocus();

      btnManter = new JButton("Manter");
      btnManter.setBounds(470, 515, 140, 35);
      btnManter.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            stand();
         }
      });
      frame.getContentPane().add(btnManter);

      btnContinuar = new JButton("Continuar");
      btnContinuar.setEnabled(false);
      btnContinuar.setVisible(false);
      btnContinuar.setBounds(290, 444, 320, 35);
      btnContinuar.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            acceptOutcome();
         }
      });
      frame.getContentPane().add(btnContinuar);

      lblVlrAposta = new JLabel();
      lblVlrAposta.setText("$" + betAmount);
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

      dealerHiddenCard = deck.takeCard();
      playSE(aa.cardSound01);
      dealerCards.cards.add(new Cards("", "", 0));
      dealerCards.cards.add(deck.takeCard());
      playSE(aa.cardSound01);

      playerCards.cards.add(deck.takeCard());
      playSE(aa.cardSound01);
      playerCards.cards.add(deck.takeCard());
      playSE(aa.cardSound01);

      updateCardPanels();
      simpleOutcomes();

   }

   public static void hit() {

      playerCards.cards.add(deck.takeCard());
      playSE(aa.cardSound01);
      updateCardPanels();

      simpleOutcomes();

   }

   public static boolean simpleOutcomes() {
      boolean outcomeHasHappened = false;
      int playerScore = playerCards.getTotalValue();
      if (playerScore > 21 && playerCards.getNumAces() > 0)
         playerScore -= 10;

      if (playerScore == 21) {

         dealerCards.cards.set(0, dealerHiddenCard);
         updateCardPanels();
         if (dealerCards.getTotalValue() == 21) {
            playSE(aa.draw);
            lblInfo.setText("Empate!");
            saldo += betAmount;
         } else {
            playSE(aa.youwon);
            lblInfo.setText(String.format("Você tem o Blackjack! Lucro: $%.2f", 1.5f * betAmount));
            saldo += 2.5f * betAmount;
         }
         lblVlrSaldo.setText(String.format("$%.2f", saldo));

         outcomeHasHappened = true;
         outcomeHappened();

      } else if (playerScore > 21) {
         playSE(aa.youlost);
         lblInfo.setText("Você Perdeu! Perda: $" + betAmount);
         dealerCards.cards.set(0, dealerHiddenCard);
         updateCardPanels();
         outcomeHasHappened = true;
         outcomeHappened();
      }
      return outcomeHasHappened;

   }

   public static void stand() {
      if (simpleOutcomes())
         return;

      int playerScore = playerCards.getTotalValue();
      if (playerScore > 21 && playerCards.getNumAces() > 0)
         playerScore -= 10;

      dealerCards.cards.set(0, dealerHiddenCard);

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
         lblInfo.setText("Você Ganhou! Lucro: $" + betAmount);
         saldo += betAmount * 2;
         lblVlrSaldo.setText(String.format("$%.2f", saldo));

      } else if (dealerScore == 21) {
         playSE(aa.youlost);
         lblInfo.setText("Dealer tem o Blackjack! Perda: $" + betAmount);

      } else if (dealerScore > 21) {
         playSE(aa.youwon);
         lblInfo.setText("Dealer Perdeu! Lucro: $" + betAmount);
         saldo += betAmount * 2;
         lblVlrSaldo.setText(String.format("$%.2f", saldo));
      } else if (playerScore == dealerScore) {
         playSE(aa.draw);
         lblInfo.setText("Empate!");
         saldo += betAmount;
         lblVlrSaldo.setText(String.format("$%.2f", saldo));
      } else {
         playSE(aa.youlost);
         lblInfo.setText("Dealer Ganhou! Perda: $" + betAmount);
      }

      outcomeHappened();

   }

   public static void outcomeHappened() {

      btnComprar.setEnabled(false);
      btnManter.setEnabled(false);

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

   public static void acceptOutcome() {

      lblInfo.setOpaque(false);
      lblInfo.setForeground(Color.ORANGE);

      frame.getContentPane().remove(lblDealer);
      frame.getContentPane().remove(lblJogador);
      frame.getContentPane().remove(btnComprar);
      frame.getContentPane().remove(btnManter);
      frame.getContentPane().remove(lblVlrAposta);
      frame.getContentPane().remove(lblVlrApostaDesc);
      frame.getContentPane().remove(btnContinuar);
      frame.getContentPane().remove(dealerCardPainel);
      frame.getContentPane().remove(playerCardPainel);
      lblInfo.setText("Insira um valor e clique em Aposta");
      tfVlrAposta.setEnabled(true);
      btnDeal.setEnabled(true);
      btnDeal.requestFocus();
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