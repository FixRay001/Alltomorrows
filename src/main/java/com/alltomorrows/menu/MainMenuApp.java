package com.alltomorrows.menu;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public final class MainMenuApp {
    private static final Color PAPER = new Color(222, 203, 163);
    private static final Color INK = new Color(31, 25, 18);
    private static final Color PANEL = new Color(23, 20, 18, 218);
    private static final Color ACCENT = new Color(205, 146, 58);
    private static final Color MUTED = new Color(176, 158, 125);

    private MainMenuApp() {
    }

    public static void main(String[] args) {
        if (args.length > 0 && "--smoke-test".equals(args[0])) {
            System.out.println("All Tomorrows Part I Beta menu smoke test passed");
            return;
        }
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Cannot open the desktop menu because no graphical display is available.");
            System.err.println("Run this on your computer's local desktop, or use --smoke-test in a remote terminal.");
            System.exit(1);
        }
        SwingUtilities.invokeLater(() -> {
            try {
                show();
            } catch (RuntimeException error) {
                System.err.println("Failed to open the desktop menu: " + error.getMessage());
                error.printStackTrace(System.err);
                System.exit(1);
            }
        });
    }

    private static void show() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ignored) {
            // The menu still works with the default Swing look and feel.
        }

        JFrame frame = new JFrame("All Tomorrows: Part I Beta");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1024, 640));
        frame.setContentPane(new MenuPanel(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static final class MenuPanel extends JPanel {
        private final JFrame frame;

        private MenuPanel(JFrame frame) {
            super(new GridBagLayout());
            this.frame = frame;
            setPreferredSize(new Dimension(1180, 720));
            setBorder(BorderFactory.createEmptyBorder(48, 72, 48, 72));
            addContent();
        }

        private void addContent() {
            JPanel card = new JPanel(new GridBagLayout());
            card.setOpaque(false);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(214, 168, 86, 130), 1),
                BorderFactory.createEmptyBorder(34, 44, 34, 44)
            ));

            GridBagConstraints root = new GridBagConstraints();
            root.gridx = 0;
            root.gridy = 0;
            root.weightx = 1;
            root.weighty = 1;
            root.anchor = GridBagConstraints.WEST;
            add(card, root);

            JLabel eyebrow = label("PART I BETA // FIRST CONTACT AFTERMATH", 14, Font.BOLD, ACCENT);
            card.add(eyebrow, constraints(0, 0, 1, 0, 0, new Insets(0, 0, 14, 0)));

            JLabel title = label("ALL TOMORROWS", 54, Font.BOLD, PAPER);
            card.add(title, constraints(0, 1, 1, 0, 0, new Insets(0, 0, 4, 0)));

            JLabel subtitle = label("The Qu War Protocol", 22, Font.PLAIN, MUTED);
            card.add(subtitle, constraints(0, 2, 1, 0, 0, new Insets(0, 0, 28, 0)));

            List<JButton> buttons = List.of(
                button("Новая игра", this::newGame),
                button("Продолжить", this::continueGame),
                button("Роли", this::roles),
                button("Настройки", this::settings),
                button("Выход", this::exit)
            );

            buttons.get(1).setEnabled(false);
            buttons.get(1).setToolTipText("Сохранения появятся в следующих сборках беты.");

            for (int i = 0; i < buttons.size(); i++) {
                card.add(buttons.get(i), constraints(0, 3 + i, 1, 0, 0, new Insets(0, 0, 12, 0)));
            }

            JLabel version = label("Beta build: main menu prototype | Part I only", 13, Font.PLAIN, new Color(156, 140, 112));
            card.add(version, constraints(0, 8, 1, 0, 0, new Insets(18, 0, 0, 0)));
        }

        private void newGame(ActionEvent event) {
            JOptionPane.showMessageDialog(
                frame,
                "Начало Части 1.\n\nСледующий шаг: экран выбора роли — Учёный, Военный, Администратор, Исследователь.",
                "Новая игра",
                JOptionPane.INFORMATION_MESSAGE
            );
        }

        private void continueGame(ActionEvent event) {
            JOptionPane.showMessageDialog(frame, "Сохранения пока отключены в beta prototype.", "Продолжить", JOptionPane.INFORMATION_MESSAGE);
        }

        private void roles(ActionEvent event) {
            JOptionPane.showMessageDialog(
                frame,
                "Доступные роли Части 1:\n\n"
                    + "- Учёный: технологии и потеря человечности\n"
                    + "- Военный: защита, операции и цена победы\n"
                    + "- Администратор: ресурсы, колонии и невозможные решения\n"
                    + "- Исследователь: планеты, артефакты и последствия",
                "Роли",
                JOptionPane.INFORMATION_MESSAGE
            );
        }

        private void settings(ActionEvent event) {
            JDialog dialog = new JDialog(frame, "Настройки", true);
            JPanel panel = new JPanel(new BorderLayout(12, 12));
            panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

            JCheckBox fullscreen = new JCheckBox("Полноэкранный режим (позже)");
            fullscreen.setEnabled(false);
            JCheckBox sound = new JCheckBox("Звук", true);
            sound.setEnabled(false);

            JPanel options = new JPanel(new GridBagLayout());
            options.add(fullscreen, constraints(0, 0, 1, 0, 0, new Insets(0, 0, 8, 0)));
            options.add(sound, constraints(0, 1, 1, 0, 0, new Insets(0, 0, 8, 0)));

            JButton close = new JButton("Закрыть");
            close.addActionListener(closeEvent -> dialog.dispose());

            panel.add(options, BorderLayout.CENTER);
            panel.add(close, BorderLayout.SOUTH);
            dialog.setContentPane(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        }

        private void exit(ActionEvent event) {
            frame.dispose();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gradient = new GradientPaint(0, 0, new Color(8, 10, 16), getWidth(), getHeight(), new Color(82, 61, 38));
            g.setPaint(gradient);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(new Color(214, 176, 102, 24));
            for (int i = 0; i < 80; i++) {
                int x = (i * 137) % Math.max(getWidth(), 1);
                int y = (i * 89) % Math.max(getHeight(), 1);
                g.fill(new Ellipse2D.Double(x, y, 2 + (i % 3), 2 + (i % 3)));
            }

            g.setColor(new Color(220, 170, 82, 88));
            g.setStroke(new BasicStroke(1.4f));
            g.drawOval(getWidth() - 420, 76, 320, 320);
            g.drawOval(getWidth() - 366, 130, 212, 212);
            g.drawLine(getWidth() - 540, 250, getWidth() - 40, 250);

            g.setColor(PANEL);
            g.fillRoundRect(54, 46, 560, getHeight() - 92, 28, 28);
            g.dispose();
        }
    }

    private static JLabel label(String text, int size, int style, Color color) {
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setForeground(color);
        label.setFont(new Font(Font.SANS_SERIF, style, size));
        return label;
    }

    private static JButton button(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 46));
        button.setFocusPainted(false);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        button.addActionListener(listener);
        return button;
    }

    private static GridBagConstraints constraints(int x, int y, int width, double weightX, double weightY, Insets insets) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = insets;
        return constraints;
    }

}
