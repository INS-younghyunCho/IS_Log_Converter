package app.example.core;

import app.example.Configurer;
import app.example.data.FileType;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class UIOperator extends JFrame {

    private static JLabel filePathLabel;
    private static JButton openFileButton; // '파일 열기' 버튼
    private static String filePathAndName;

    public UIOperator() {
        JLabel instructionLabel = setComponents();

        // JLabel에 TransferHandler 설정
        instructionLabel.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                try {
                    List<File> droppedFiles = (List<File>) support.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);

                    if (!droppedFiles.isEmpty()) {
                        File file = droppedFiles.get(0);

                        if (file.getName().endsWith(".gz")) {
                            file = GzipDecompressor.decompressGzipFile(file.getAbsolutePath());
                        }

                        filePathAndName = Configurer.getFilePath(FileType.XLSX);
                        filePathLabel.setText("파일 경로: " + file.getAbsolutePath());

                        // 파일을 처리하고 저장
                        LogFileWriter.write(
                                ContentHandler.handle(file),
                                filePathAndName
                        );

                        // 처리 완료 후 '파일 열기' 버튼 텍스트를 업데이트하고 보이게 설정
                        openFileButton.setText("<html><div style='text-align: center; font-size: 110%;'>파일 열기<br>" + filePathAndName + "</div></html>");
                        openFileButton.setVisible(true);
                    }
                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });
    }

    private JLabel setComponents() {
        setTitle("IS Log Text To XLSX Converter");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 드래그 앤 드롭 안내 레이블
        JLabel instructionLabel = new JLabel("여기에 압축 파일(.gz) 또는 로그 파일(.log)을 드래그 앤 드롭하세요", SwingConstants.CENTER);
        instructionLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        instructionLabel.setPreferredSize(new Dimension(400, 50));

        // 메인 패널에 추가
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(instructionLabel, BorderLayout.CENTER); // 드래그 앤 드롭 안내 레이블
        add(mainPanel, BorderLayout.CENTER);

        // 파일 경로를 표시할 레이블
        filePathLabel = new JLabel("파일 경로:", SwingConstants.CENTER);
        filePathLabel.setPreferredSize(new Dimension(400, 30));
        add(filePathLabel, BorderLayout.SOUTH);

        // '파일 열기' 버튼 추가
        openFileButton = new JButton("파일 열기");
        openFileButton.setVisible(false); // 초기에는 숨김 상태
        openFileButton.addActionListener(e -> openFile(filePathAndName));
        add(openFileButton, BorderLayout.NORTH);
        return instructionLabel;
    }

    private void openFile(String filePath) {
        try {
            Desktop.getDesktop().open(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
