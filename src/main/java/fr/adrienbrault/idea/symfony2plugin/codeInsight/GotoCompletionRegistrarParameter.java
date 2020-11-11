package fr.adrienbrault.idea.symfony2plugin.codeInsight;

public interface GotoCompletionRegistrarParameter {
    void register(GotoCompletionAccepter accepter, GotoCompletionContributor contributor);
}
