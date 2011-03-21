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
    CHST3(8),
    PD(9),
    PTR(10),
    MODE(11),
    SF_1(12),
    SF_2(13),
    SF_3(14),
    SF_4(15);

    private final int value;

    private Register(int value)
    {
        this.value = value;
    }
}
