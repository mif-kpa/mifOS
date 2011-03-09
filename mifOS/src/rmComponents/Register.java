/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rmComponents;

/**
 *
 * @author Karolis Voicechovskis PS3
 */
public enum Register
{
    R(1),
    M(2),
    IC(3),
    SF(4),
    CHST1(5),
    CHST2(6),
    CHST3(7);

    private final int value;

    private Register(int value)
    {
        this.value = value;
    }
}
