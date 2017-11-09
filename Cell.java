/* Represents a single cell on the board */
class Cell
{
    private boolean flagged, covered;
    private int count;
    private boolean traversed;

    Cell() {
        flagged = false;
        covered = true;
        traversed = false;
        count = 0;
    }

    public boolean isMine() {
            return count==9;
    }

    public boolean beenTraversed() {
            return traversed;
    }

    public void setTraversed(boolean traversed) {
            this.traversed = traversed;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isCovered() {
        return covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
