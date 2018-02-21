package common;

public class Configuration {

    public static class Paths{

        public static String get_MainPath(){
            return "../views/main/mainView.fxml";
        }

        public static String get_MainCss(){
            return "../views/main/main.css";
        }

        public static String get_AddPartPath(){ return "/views/parts/AddEditPartView.fxml"; }
        public static String get_ModifyPartPath(){
            return "/views/parts/AddEditPartView.fxml";
        }
        public static String get_ListPartPath() { return "../views/parts/ListPartView.fxml"; }
        public static String get_TablePartPath() {return "../views/parts/TablePartView.fxml";}


        public static String get_AddProductPath(){ return "/views/products/AddEditProductView.fxml"; }
        public static String get_ModifyProductPath(){ return "/views/products/AddEditProductView.fxml"; }
        public static String get_ListProductPath() { return "../views/products/ListProductView.fxml"; }

        public static String get_ErrorPath() {
            return "../views/main/Error.fxml";
        }
    }

    public static class WindowDimensions{

        public static int get_SmallWindowWidth(){
            return 500;
        }

        public static int get_SmallWindowHeight(){
            return 500;
        }

        public static int get_StandardWindowWidth(){
            return 600;
        }

        public static int get_StandardWindowHeight(){
            return 500;
        }

        public static int get_LargeWindowWidth(){
            return 1400;
        }

        public static int get_LargeWindowHeight(){
            return 600;
        }


    }
}
