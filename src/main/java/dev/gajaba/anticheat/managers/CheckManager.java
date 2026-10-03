package dev.gajaba.anticheat.managers;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.combat.aura.AuraA;
import dev.gajaba.anticheat.checks.combat.aura.AuraB;
import dev.gajaba.anticheat.checks.combat.aura.AuraC;
import dev.gajaba.anticheat.checks.combat.aura.AuraD;
import dev.gajaba.anticheat.checks.combat.aura.AuraE;
import dev.gajaba.anticheat.checks.combat.aura.AuraF;
import dev.gajaba.anticheat.checks.combat.autoblock.AutoBlockA;
import dev.gajaba.anticheat.checks.combat.autoclicker.AutoClickerA;
import dev.gajaba.anticheat.checks.combat.autoclicker.AutoClickerB;
import dev.gajaba.anticheat.checks.combat.autoclicker.AutoClickerC;
import dev.gajaba.anticheat.checks.combat.autoclicker.AutoClickerD;
import dev.gajaba.anticheat.checks.combat.autoclicker.AutoClickerE;
import dev.gajaba.anticheat.checks.combat.criticals.CriticalsA;
import dev.gajaba.anticheat.checks.combat.reach.ReachA;
import dev.gajaba.anticheat.checks.combat.reach.ReachB;
import dev.gajaba.anticheat.checks.combat.reach.ReachC;
import dev.gajaba.anticheat.checks.combat.velocity.VelocityA;
import dev.gajaba.anticheat.checks.combat.velocity.VelocityB;
import dev.gajaba.anticheat.checks.combat.velocity.VelocityC;
import dev.gajaba.anticheat.checks.movement.fly.FlyB;
import dev.gajaba.anticheat.checks.movement.fly.FlyC;
import dev.gajaba.anticheat.checks.movement.fly.FlyD;
import dev.gajaba.anticheat.checks.movement.fly.FlyE;
import dev.gajaba.anticheat.checks.movement.bunnyhop.BunnyHopA;
import dev.gajaba.anticheat.checks.movement.crouch.CrouchA;
import dev.gajaba.anticheat.checks.movement.jesus.JesusA;
import dev.gajaba.anticheat.checks.movement.nofall.NoFallA;
import dev.gajaba.anticheat.checks.movement.nofall.NoFallB;
import dev.gajaba.anticheat.checks.movement.noslow.NoSlowA;
import dev.gajaba.anticheat.checks.movement.noslow.NoSlowB;
import dev.gajaba.anticheat.checks.movement.phase.PhaseA;
import dev.gajaba.anticheat.checks.movement.phase.PhaseB;
import dev.gajaba.anticheat.checks.movement.predict.PredictA;
import dev.gajaba.anticheat.checks.movement.speed.SpeedB;
import dev.gajaba.anticheat.checks.movement.speed.SpeedC;
import dev.gajaba.anticheat.checks.movement.speed.SpeedD;
import dev.gajaba.anticheat.checks.movement.spider.SpiderA;
import dev.gajaba.anticheat.checks.movement.step.StepA;
import dev.gajaba.anticheat.checks.packet.badpackets.BadPacketsA;
import dev.gajaba.anticheat.checks.packet.badpackets.BadPacketsB;
import dev.gajaba.anticheat.checks.packet.badpackets.BadPacketsC;
import dev.gajaba.anticheat.checks.packet.blink.BlinkA;
import dev.gajaba.anticheat.checks.packet.pingspoof.PingSpoofA;
import dev.gajaba.anticheat.checks.world.fastplace.FastPlaceA;
import dev.gajaba.anticheat.checks.world.invmove.InvMoveA;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CheckManager {

    private final List<Check> checks = new ArrayList<Check>();

    public CheckManager(GajabaLegacy plugin) {
        checks.add(new AuraA(plugin));
        checks.add(new AuraB(plugin));
        checks.add(new AuraC(plugin));
        checks.add(new AuraD(plugin));
        checks.add(new AuraE(plugin));
        checks.add(new AuraF(plugin));
        checks.add(new AutoBlockA(plugin));
        checks.add(new AutoClickerA(plugin));
        checks.add(new AutoClickerB(plugin));
        checks.add(new AutoClickerC(plugin));
        checks.add(new AutoClickerD(plugin));
        checks.add(new AutoClickerE(plugin));
        checks.add(new CriticalsA(plugin));
        checks.add(new ReachA(plugin));
        checks.add(new ReachB(plugin));
        checks.add(new ReachC(plugin));
        checks.add(new VelocityA(plugin));
        checks.add(new VelocityB(plugin));
        checks.add(new VelocityC(plugin));
        checks.add(new FlyB(plugin));
        checks.add(new FlyC(plugin));
        checks.add(new FlyD(plugin));
        checks.add(new FlyE(plugin));
        checks.add(new BunnyHopA(plugin));
        checks.add(new CrouchA(plugin));
        checks.add(new PredictA(plugin));
        checks.add(new JesusA(plugin));
        checks.add(new SpeedB(plugin));
        checks.add(new SpeedC(plugin));
        checks.add(new SpeedD(plugin));
        checks.add(new StepA(plugin));
        checks.add(new SpiderA(plugin));
        checks.add(new NoFallA(plugin));
        checks.add(new NoFallB(plugin));
        checks.add(new NoSlowA(plugin));
        checks.add(new NoSlowB(plugin));
        checks.add(new PhaseA(plugin));
        checks.add(new PhaseB(plugin));
        checks.add(new BadPacketsA(plugin));
        checks.add(new BadPacketsB(plugin));
        checks.add(new BadPacketsC(plugin));
        checks.add(new PingSpoofA(plugin));
        checks.add(new BlinkA(plugin));
        checks.add(new FastPlaceA(plugin));
        checks.add(new InvMoveA(plugin));
    }

    public void runGeneralChecks(PlayerData data) {
        for (Check check : checks) {
            check.handle(data);
        }
    }

    public void runCombatChecks(PlayerData data, Entity target) {
        for (Check check : checks) {
            check.handle(data, target);
        }
    }

    public List<Check> getChecks() {
        return Collections.unmodifiableList(checks);
    }
}
