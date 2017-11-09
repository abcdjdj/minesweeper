/* Interface for all constants */
interface BoardConstantsInterface {
        public static final int BOARD_ROWS = 5;//16;
        public static final int BOARD_COLS = 5;//16;
        public static final int IMAGES_NUMBER = 13;
        public static final int CELL_SIZE = 15;
        public static final double PROBABILITY = 0.12;

        /* Cell States */
        public static final int IMG_COVERED = 10;
        public static final int IMG_FLAGGED = 11;
        public static final int IMG_FLAGGED_WRONG = 12;
}
