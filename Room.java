public final class Room {

    private static final char[][] MATRIX = {
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
            { '#', '_', '_', '_', '_', '_', '#', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '#', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '#', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '#', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '_', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '_', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '_', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '_', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '_', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '_', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '_', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '#', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '#', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '#', '_', '_', '#' },
            { '#', '_', '_', '_', '_', '_', '#', '_', '_', '#' },
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
    };
	
    private static final int 
		BOUNDARY_X = MATRIX[0].length - 1,
		BOUNDARY_Y = MATRIX.length - 1;

    private Room() {

    }

    public static boolean isVoid(final double px, final double py) {
        final int x = (int) px;
        final int y = (int) py;
        return y > 0
                && x > 0
                && y < BOUNDARY_Y
                && x < BOUNDARY_X
                && MATRIX[y][x] == '_';
    }

}
