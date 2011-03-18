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
    S(4),
    SF(5),
    CHST1(6),
    CHST2(7),
    CHST3(8);

    private final int value;

    private Register(int value)
    {
        this.value = value;
    }
}
