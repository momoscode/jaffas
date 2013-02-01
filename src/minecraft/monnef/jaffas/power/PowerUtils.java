package monnef.jaffas.power;

import monnef.jaffas.power.api.IPowerConsumer;
import monnef.jaffas.power.api.IPowerProvider;
import net.minecraftforge.common.ForgeDirection;

public class PowerUtils {
    public static final int LOSE_COEFFICIENT_MULTIPLIER = 100;

    public static boolean Connect(IPowerProvider provider, IPowerConsumer consumer, ForgeDirection providersSide) {
        boolean success = true;
        if (providersSide == ForgeDirection.UNKNOWN) {
            success &= provider.getPowerProviderManager().connect(consumer);
            consumer.getPowerConsumerManager().connect(provider);
        } else {
            success &= provider.getPowerProviderManager().connectDirect(consumer, providersSide);
            consumer.getPowerConsumerManager().connectDirect(provider, providersSide.getOpposite());
        }

        return success;
    }

    public static void Disconnect(IPowerProvider provider, IPowerConsumer consumer) {
        if (provider == null || consumer == null) return;
        provider.getPowerProviderManager().disconnect(consumer);
        consumer.getPowerConsumerManager().disconnect();
    }

    public static int loseEnergy(int energy, int distance) {
        return (energy * getLoseCoefficient(distance)) / LOSE_COEFFICIENT_MULTIPLIER;
    }

    public static int getLoseCoefficient(int distance) {
        if (distance <= 10) return 100;
        if (distance <= 20) return 80;
        if (distance <= 30) return 40;
        return 0;
    }
}
