package coten.GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainGUI extends Application {
    private Controller controller;
    private ChamberEdit chEdit;
    private PassageEdit pEdit;
    private ChamberDoorPopup cDoorPop;
    private PassageDoorPopup pDoorPop;
    private ListView<String> chamberList;
    private ListView<String> passageList;
    private ListView<String> doors;
    private Menu menu;
    private MenuBar menuBar;
    private BorderPane borderPane;
    private FileChooser fileChooser;
    private Stage mainStage;
    private GridPane grid;

    private Button edit;

    private Label description;

    /**
     * A function to be run just before the gui is made.
     */
    @Override
    public void init() {
        controller = new Controller(this);
        controller.createLevel();
    }

    /**
     * The main to be executed by JavaFx.
     * @param args Command line arugments.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * The starting function for the GUI.
     * @param stage The inital Stage Given by JavFx
     */
    @Override
    public void start(Stage stage) {
        mainStage = stage;
        borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 1280, 720);
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("dungeon Files (*.dungeon)", "*.dungeon"));


        edit = new Button("Edit Selection");

        grid = new GridPane();
        grid.setMinSize(500, 500);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.TOP_CENTER);
        VBox center = new VBox(grid);

        description = new Label("Click on a space to view it!");
        center.getChildren().add(description);
        borderPane.setCenter(center);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        borderPane.setLeft(vBox);


        doors = new ListView<String>();
        chamberList = chamberPanel();
        passageList = passagePanel();
        vBox.getChildren().add(chamberList);
        vBox.getChildren().add(passageList);
        chEdit = new ChamberEdit(stage, controller);
        pEdit = new PassageEdit(stage, controller);
//
//        HBox editH = new HBox();
//        editH.getChildren().add(edit);

        vBox.getChildren().add(edit);
        vBox.setPadding(new Insets(25, 25, 25, 25));


        HBox hBoxRight = new HBox();
        borderPane.setRight(hBoxRight);
        cDoorPop = new ChamberDoorPopup(stage, controller);
        pDoorPop = new PassageDoorPopup(stage, controller);
        hBoxRight.getChildren().add(doors);

        initStage(stage);

        createMenu();

        //Change onclick function of button depending on which list if chosen

        stage.setScene(scene);
        stage.show();
    }

    private void initStage(Stage stage) {
        stage.setTitle("Dungeon Generator");
    }

    private void updateEditButton(String text, Edit editPopup) {
        edit.setText(text);
        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editPopup.show();
            }
        });

    }

    /**
     * Updates the door list. Specific for chambers.
     */
    public void updateChamberDoorList() {
        doors.getItems().clear();
        for (int i = 0; i < controller.getChamberDoors(chamberList.getSelectionModel().getSelectedIndex()).size(); i++) {
            doors.getItems().add("Door " + (i + 1));
        }
    }

    /**
     * Updates the list of doors on the right. This is specific for passages.
     */
    public void updatePassageDoorList() {
        doors.getItems().clear();
        for (int i = 0; i < controller.getPassageDoors(passageList.getSelectionModel().getSelectedIndex()).size(); i++) {
            doors.getItems().add("Door " + (i + 1));
        }
    }
    private void doorListPopup(DoorPopup popUp) {
        doors.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (doors.getSelectionModel().getSelectedIndex() >= 0) {
                    if (popUp instanceof ChamberDoorPopup) {
                        ((ChamberDoorPopup) (popUp)).setDoorId(doors.getSelectionModel().getSelectedIndex());
                    }
                    if (popUp instanceof PassageDoorPopup) {
                        ((PassageDoorPopup) (popUp)).setDoorId(doors.getSelectionModel().getSelectedIndex());
                    }
                    popUp.show();
                }
            }
        });
    }

    private void createMenu() {
        menuBar  = new MenuBar();
        menu = new Menu("File");
        borderPane.setTop(menuBar);

        MenuItem save = new MenuItem("Save Dungeon");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("You clicked on save!");
                fileChooser.setTitle("Select a location to save");
                controller.saveDungeon(fileChooser.showSaveDialog(mainStage));
            }
        });

        MenuItem load = new MenuItem("Load Dungeon");
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("You clicked on load!");
                fileChooser.setTitle("Select a file to load");
                controller.loadDungeon(fileChooser.showOpenDialog(mainStage));
            }
        });
        menu.getItems().addAll(save, load);
        menuBar.getMenus().add(menu);

    }
    private ListView<String> chamberPanel() {
        chamberList = new ListView<String>();
        updateChamberList();

        chamberList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO write handle description function
                if (chamberList.getSelectionModel().getSelectedIndex() >= 0) {
                    chamberListClick();
                }
            }
        });
        return chamberList;
    }
    private int placeDoor(int i, int j, int doorCounter) {
        if (i == 0 && j == 0) {
            grid.add(new DoorTile((i + j) % 2, 0, this.mainStage, controller),  i * 2, j * 2, 2, 2);
            doorCounter++;
        } else if (i == 0 && j == controller.getLength(chamberList.getSelectionModel().getSelectedIndex()) / 10 - 1) {
            grid.add(new DoorTile((i + j) % 2, 0, this.mainStage, controller), i * 2, j * 2, 2, 2);
            doorCounter++;
        } else if (i == controller.getWidth(chamberList.getSelectionModel().getSelectedIndex()) / 10 - 1 && j == 0) {
            grid.add(new DoorTile((i + j) % 2, 1, this.mainStage, controller), i * 2,  j * 2, 2, 2);
            doorCounter++;
        } else if (i == controller.getWidth(chamberList.getSelectionModel().getSelectedIndex()) / 10 - 1 && j == controller.getLength(chamberList.getSelectionModel().getSelectedIndex()) / 10 - 1) {
            grid.add(new DoorTile((i + j) % 2, 1, this.mainStage, controller), i * 2, j * 2, 2, 2);
            doorCounter++;
        } else {
            grid.add(new Tile((i + j) % 2), i * 2, j * 2, 2, 2);
        }
        return doorCounter;
    }
    private int chambRandWidth() {
        return  (int) (Math.random() * (((controller.getWidth(chamberList.getSelectionModel().getSelectedIndex()) / 10 - 1)) + 1));
    }
    private int chambRandLength() {
        return  (int) (Math.random() * (((controller.getLength(chamberList.getSelectionModel().getSelectedIndex()) / 10 - 1)) + 1));
    }

    /**
     * Updates the drawing of a chamber in the center of the screen.
     */
    public void updateChamberDrawing() {
        grid.getChildren().clear();
        int doorCounter = 0;
        grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        for (int i = 0; i < controller.getWidth(chamberList.getSelectionModel().getSelectedIndex()) / 10; i++) {
            for (int j = 0; j < controller.getLength(chamberList.getSelectionModel().getSelectedIndex()) / 10; j++) {
                if (doorCounter < controller.getChamberDoors(chamberList.getSelectionModel().getSelectedIndex()).size()) {
                    doorCounter = placeDoor(i, j, doorCounter);
                } else {
                    grid.add(new Tile((i + j) % 2), i * 2, j * 2, 2, 2);
                }
            }
        }
        double chestI = 0;
        double chestJ = 0;
        if (controller.getChamber(chamberList.getSelectionModel().getSelectedIndex()).getTreasureList().size() > 0) {
            chestI = chambRandWidth();
            chestJ = chambRandLength();
            grid.add(new ChestTile((((int) chestI) + ((int) chestJ)) % 2), (int) chestI * 2, (int) chestJ * 2, 2, 2);
        }
        double monsterI = chestI;
        double monsterJ = chestJ;
        int collisionCount = 0;
        if (controller.getChamber(chamberList.getSelectionModel().getSelectedIndex()).getMonsters().size() > 0) {
            while (monsterI == chestI && monsterJ == chestJ && collisionCount < 10) {
                monsterI = chambRandWidth();
                monsterJ = chambRandLength();
                collisionCount++;
            }
            grid.add(new MonsterTile((((int) monsterI) + ((int) monsterJ)) % 2), (int) monsterI * 2, (int) monsterJ * 2, 2, 2);
        }

    }

    private int getRandSection() {
        int min = 0;
        int max = controller.getPassage(passageList.getSelectionModel().getSelectedIndex()).getPassageSections().size() - 1;
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    /**
     * updates the drawing for passages.
     */
    public void updatePassageDrawing() {
        grid.getChildren().clear();
        for (int i = 0; i < controller.getPassage(passageList.getSelectionModel().getSelectedIndex()).getPassageSections().size(); i++) {
            grid.add(new DoorTile(0, i%2, this.mainStage, controller), 0, i * 2, 2, 2);
        }

        double chestI = 0;
        double chestJ = 0;
        if (controller.getPassage(passageList.getSelectionModel().getSelectedIndex()).getTreasure().size() > 0) {
            chestI = 0;
            chestJ = getRandSection();
            grid.add(new ChestTile((((int) chestI) + ((int) chestJ)) % 2), (int) chestI * 2, (int) chestJ * 2, 2, 2);
        }
        double monsterI = chestI;
        double monsterJ = chestJ;
        int collisionCount = 0;
        if (controller.getPassage(passageList.getSelectionModel().getSelectedIndex()).getMonsters().size() > 0) {
            while (monsterI == chestI && monsterJ == chestJ && collisionCount < 10) {
                monsterI = 0;
                monsterJ = getRandSection();
                collisionCount++;
            }
            grid.add(new MonsterTile((((int) monsterI) + ((int) monsterJ)) % 2), (int) monsterI * 2, (int) monsterJ * 2, 2, 2);
        }

    }
    private void chamberListClick() {
        //TODO update Drawing
        updateChamberDrawing();
        updateChamberDoorList();
        updateChamberDescription(chamberList.getSelectionModel().getSelectedIndex());

        chEdit.setChamber(chamberList.getSelectionModel().getSelectedIndex());
        updateEditButton("Edit Chamber", chEdit);

        cDoorPop.setChamberId(chamberList.getSelectionModel().getSelectedIndex());
        doorListPopup(cDoorPop);
    }

    private void passageListClick() {
        //TODO update drawing
        updatePassageDrawing();
        updatePassageDoorList();
        updatePassageDescription(passageList.getSelectionModel().getSelectedIndex());

        pEdit.setPassgage(passageList.getSelectionModel().getSelectedIndex());
        updateEditButton("Edit Passage", pEdit);

        pDoorPop.setPassageId(passageList.getSelectionModel().getSelectedIndex());
        doorListPopup(pDoorPop);
    }

    /**
     * Updates the list of chambers from the left side.
     */
    public void updateChamberList() {
        description.setText("");
        grid.getChildren().clear();
        chamberList.getItems().clear();
        for (int i = 0; i < controller.getChambers().size(); i++) {
            chamberList.getItems().add("Chamber ID<" + controller.getChambers().get(i).getId() + ">");
        }
    }

    /**
     * Selects a specific chamber.
     * @param index the chamber to be selected
     */
    public void selectChamber(int index) {
        chamberList.getSelectionModel().select(index);
        chamberListClick();
    }

    /**
     * selected a specific passage.
     * @param index the id of passage to select
     */
    public void selectPassage(int index) {
        passageList.getSelectionModel().select(index);
        passageListClick();
    }

    /**
     * Updates the side list of passages from the currently selected passage.
     */
    public void updatePassageList() {
        description.setText("");
        grid.getChildren().clear();
        passageList.getItems().clear();
        for (int i = 0; i < controller.getPassages().size(); i++) {
            passageList.getItems().add("Passage ID<" + controller.getPassage(i).getPassageID() + ">");
        }
    }
    private ListView<String> passagePanel() {

        passageList = new ListView<String>();
        updatePassageList();

        passageList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO write handle description function
                if (passageList.getSelectionModel().getSelectedIndex() >= 0) {
                    passageListClick();
                }
            }
        });

        return passageList;
    }

    /**
     * Updates the description area for a chamber.
     * @param chamberID which chamber to get the description from
     */
    public void updateChamberDescription(int chamberID) {
        description.setText(controller.getChamber(chamberID).getDescription());
    }

    /**
     * Updates the description area for a passage.
     * @param passageID the id of which passage to use
     */
    public void updatePassageDescription(int passageID) {
        description.setText(controller.getPassage(passageID).getDescription());
    }

}
