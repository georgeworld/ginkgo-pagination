/*
 * Programming by: George <GeorgeNiceWorld@gmail.com>
 * Copyright (C) George(www.georgeinfo.com), All Rights Reserved.
 */
package mainentry;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import pagination.annotation.PaginationCommentItem;
import pagination.annotation.PaginationComments;

/**
 *
 * @author George <GeorgeNiceWorld@gmail.com>
 * @contact http://www.georgeinfo.com
 * @since 2012-11-20
 */
@PaginationComments({
    @PaginationCommentItem(dateTime = "2013-10-30 13:56", version = "v1.0.0", notes = {"创建项目"})
})
public class PaginationMain {

    public static final String VERSION = "v1.0.0";
    public static final String UPDATE_DATE_TIME = "2013-10-30 13:56";

    public PaginationMain() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Georgeinfo-JDBC");
        mainFrame.setSize(599, 300);
        mainFrame.setLayout(new java.awt.BorderLayout());

        JLabel topLabel = new JLabel("乔治 通用分页 封装包 " + getVersion());
        topLabel.setFont(new java.awt.Font("宋体", 1, 18));
        topLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 102, 102), new java.awt.Color(0, 204, 204)));

        JLabel bottomLabel = new JLabel("(C)www.georgeinfo.com");
        bottomLabel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                openUrl("http://www.georgeinfo.com");
            }

            public void mousePressed(MouseEvent e) {
//                openUrl("http://www.georgeinfo.com");
            }

            public void mouseReleased(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseEntered(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseExited(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        bottomLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bottomLabel.setForeground(Color.BLUE);

//        JTextArea changeLogArea = new JTextArea();
//        changeLogArea.setText(IrapidCommentsTool.getCommentsString(IrapidMain.class));
//        changeLogArea.setFont(new java.awt.Font("宋体", 0, 18));
//        JScrollPane mainScrollPane = new JScrollPane();
//        mainScrollPane.setViewportView(changeLogArea);
        mainFrame.add(topLabel, java.awt.BorderLayout.NORTH);
        mainFrame.add(new PaginationChangeLogPanel(PaginationMain.class), java.awt.BorderLayout.CENTER);
        mainFrame.add(bottomLabel, java.awt.BorderLayout.SOUTH);

        mainFrame.setAlwaysOnTop(true);
        setWindowCenter(mainFrame);
        mainFrame.setVisible(true);
    }

    public static String getVersion() {
        return "[" + VERSION + "]  build [" + UPDATE_DATE_TIME + "]";
    }

    /*
     * 使窗口居中显示
     */
    public static void setWindowCenter(Window window) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int w = window.getWidth();
        int h = window.getHeight();
        window.setLocation((width - w) / 2, (height - h) / 2);
    }

    public static void openUrl(String urlStr) {
        try {
            //帮助
            URI url = new URI(urlStr);
            java.awt.Desktop.getDesktop().browse(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
}
