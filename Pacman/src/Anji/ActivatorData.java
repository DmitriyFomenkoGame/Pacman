package Anji;

import com.anji.util.Properties;

public class ActivatorData {

	public void init(Properties properties) {
		/* Mogelijke properties:
		 * 
		 * Booleans voor dingen wel/niet meegeven aan het neurale netwerk:
		 * - Pacman
		 * - Blinky / Pinky / Inky / Clyde
		 * - Walls
		 * - Dots
		 * - Energizers
		 * - 'Gameticks remaining'
		 * - Score
		 * - Scatter vs chase vs fright mode
		 * - Pacmandirection
		 * - Ghostdirection
		 * - ...
		 * 
		 * TODO Uitzoeken of het uitmaakt of je een grote 1D array meegeeft of een 2D array
		 * Het formatten van de data:
		 * - Binaire grids vs 'één grid'
		 * - Volledige bord vs zichtveld (vb zichtveld == -1 --> volledige bord en zichtveld > 0 --> aantal pixels naar links/rechts/boven/onder, dus 1 is een 3*3 rondom pacman)
		 * - ...
		 * 
		 */
	}

}
