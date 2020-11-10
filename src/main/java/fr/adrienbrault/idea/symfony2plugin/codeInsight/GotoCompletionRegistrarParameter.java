package fr.adrienbrault.idea.symfony2plugin.codeInsight;

public interface GotoCompletionRegistrarParameter {
    public void register(GotoCompletionAccepter accepter, GotoCompletionContributor contributor);
}
